package com.sergiop.aparcamientos.data

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.sergiop.aparcamientos.extensions.distanceKm
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebase : FirebaseFirestore) : DataRepository {

    override suspend fun registerUser (persona : Persona) : Result<String> {

        return withContext(Dispatchers.IO) {
            val taskDocument = firebase .collection("personas")
                                        .add(persona.toMap())

            val documet = taskDocument?.await()
            if (taskDocument.isSuccessful) {
                Result.success(documet.id)
            } else {
                Result.failure(taskDocument.exception?.cause?: Throwable("Error in Firebase connection adding"))
            }
        }
    }

    override suspend fun loginUser (user: String, password: String) : Result<Persona?> {

        return withContext(Dispatchers.IO) {

            val task = firebase.collection("personas")
                                .whereEqualTo("username", user)
                                .whereEqualTo("password", password)
                                .limit(1).get()

            val document = task?.await()
            if (task?.isSuccessful) {
                if (document.size() > 0) {
                    val usuarioLogged = Persona()
                    usuarioLogged.username = task.result.documents[0]["username"].toString()
                    usuarioLogged.password = task.result.documents[0]["password"].toString()
                    usuarioLogged.imagen = task.result.documents[0]["imagen"].toString()
                    usuarioLogged.id = task.result.documents[0].id

                    Result.success(usuarioLogged)
                } else {
                    Result.failure(Throwable("Error in Firebase connection login"))
                }
            } else {
                Result.failure(task?.exception?.cause?: Throwable("Error in Firebase connection login"))
            }

        }
    }

    private suspend fun requestPersona (personaReference : DocumentReference?) : Result<Persona> {
        return withContext(Dispatchers.IO) {

            val task = personaReference?.get()

            val document = task?.await()
            if (task?.isSuccessful == true) {
                val persona = Persona()
                persona.username = document?.get("username").toString()
                persona.password = document?.get("password").toString()
                persona.imagen = document?.get("imagen").toString()

                Result.success(persona)
            } else {
                Result.failure(task?.exception?.cause?: Throwable("Error in Firebase connection login"))
            }
        }
    }

    override suspend fun registerParking (garaje : Garaje) : Result<String> {
        return withContext(Dispatchers.IO) {
            val taskDocument = firebase.collection("garajes")
                                        .add(garaje.toMap())

            val document = taskDocument?.await()
            if (taskDocument.isSuccessful) {
                document.update("propietario", firebase.collection("personas").document(garaje.propietario?.id ?: ""))
                Result.success(document.id)
            } else {
                Result.failure(taskDocument.exception?.cause?: Throwable("Error in Firebase connection adding"))
            }
        }
    }

    override suspend fun editImageUser(persona: Persona, imageUrl: String): Result<Boolean> {

        return withContext(Dispatchers.IO) {

            val task = firebase.collection("personas")
                .whereEqualTo("username", persona.username)
                .whereEqualTo("password", persona.password)
                .get()

            val document = task?.await()
            if (task?.isSuccessful) {
                if (document.size() > 0) {
                    document.documents.get(0).reference.update("imagen", imageUrl)
                    Result.success(true)
                } else {
                    Result.failure(Throwable("Error in Firebase connection login"))
                }
            } else {
                Result.failure(task?.exception?.cause?: Throwable("Error in Firebase connection login"))
            }
        }

    }

    override suspend fun allParking(propietario: Persona?): Result<List<Garaje>> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<Garaje>()
            var taskDocument : Task<QuerySnapshot>
            if (propietario != null) {

                val collectionRef = firebase.collection("personas").document(propietario.id)

                taskDocument = firebase.collection("garajes")
                    .whereEqualTo("propietario", collectionRef)
                    .get()
            } else {
                taskDocument = firebase.collection("garajes")
                    .get()
            }

            val document = taskDocument?.await()
            if (taskDocument.isSuccessful) {
                for (document in document.documents) {

                    val referencePropietario = document.getDocumentReference("propietario")?: null

                    val garaje = Garaje(
                        id = document.id,
                        moto = document.getBoolean("moto")?:false,
                        direccion = document.getString("direccion")?: "",
                        largo = (document.getDouble("largo")?:0.0).toFloat(),
                        latitud = (document.getDouble("latitud")?:0.0),
                        longitud = (document.getDouble("longitud")?:0.0),
                        propietarioReference = referencePropietario.toString(),
                        propietario = requestPersona(referencePropietario).getOrNull()
                    )

                    list.add(garaje)
                }

                Result.success(list)
            } else {
                Result.failure(Throwable("Request not satisfied"))
            }
        }
    }

    override suspend fun deleteParking (garaje: Garaje) : Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val taskDocument = firebase.collection("garajes").document(garaje.id).delete()

            taskDocument?.await()
            if (taskDocument.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Throwable("Request not satisfied"))
            }
        }
    }

    override suspend fun updateParking(
        direccion: String, longitud : Double, latitud : Double, largo: Float, ancho: Float, moto: Boolean
                                        ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val taskDocument = firebase.collection("garajes")
                .whereEqualTo("direccion", direccion)
                .whereEqualTo("longitud", longitud)
                .whereEqualTo("latitud", latitud)
                .whereEqualTo("ancho", ancho)
                .whereEqualTo("largo", largo)
                .whereEqualTo("moto", moto).get()
            val documents = taskDocument?.await()
            if(taskDocument.isSuccessful){
                val garaje : Garaje
                for(document in documents.documents){
                    //document.reference.update(garaje.toMap())
                }
                Result.success(true)
            } else {
                Result.failure(Throwable("Request not satisfied"))
            }
        }
    }

    override suspend fun updatePropietario(garage : Garaje, persona: Persona) {
        return withContext(Dispatchers.IO) {

            val taskDocument = firebase .collection("garajes")
                                        .document(garage.id)
                                        .update(mapOf(
                                            "propietario" to firebase.collection("personas").document(persona.id),
                                        ))

            taskDocument?.await()
            if(taskDocument.isSuccessful){
                Result.success(true)
            } else {
                Result.failure(Throwable("Request not satisfied"))
            }
        }
    }

    override suspend fun getGarageNear(locationObject: Location, radio : Int): Result<Garaje?> {
        return withContext((Dispatchers.IO)) {

            val result = allParking(null)
            if (result.isSuccess) {
                val listParkings = result.getOrDefault(listOf())
                var garajeNear : Garaje? = null
                var distanceMin = Double.MAX_VALUE
                listParkings.forEach {
                    if (garajeNear == null) {
                        garajeNear = it
                    } else {
                        val distance = distanceKm(  locationObject.latitude,
                                                    locationObject.longitude,
                                                    it.latitud, it.longitud)
                        if (distance < distanceMin) {
                            garajeNear = it
                            distanceMin = distance
                        }
                    }
                }

                if (distanceMin <= radio) {
                    Result.success(garajeNear)
                } else {
                    Result.failure(Throwable("Error garage cercano no encontrado"))
                }

            } else {
                Result.failure(Throwable("Error in Firebase connection near garages"))
            }

        }
    }

}
