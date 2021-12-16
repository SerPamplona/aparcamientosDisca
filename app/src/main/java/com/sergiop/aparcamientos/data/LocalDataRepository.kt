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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataRepository @Inject constructor() : DataRepository {

    private val personasList =
        mutableListOf<Persona>(
            Persona("sergio", "6546546546", "sergio", "https://i0.wp.com/tuzonagamer.com/wp-content/uploads/2020/05/WhatsApp-Image-2020-05-18-at-2.42.44-PM-1.jpeg?resize=660%2C330&ssl=1", "3213123123"),
            Persona("moises", "6546546546", "moises", "https://cdn.icon-icons.com/icons2/1879/PNG/512/iconfinder-3-avatar-2754579_120516.png", "")
        )

    private val garageList =
        mutableListOf<Garaje>(
            Garaje(moto = true, largo = 342.0f, ancho = 4234.0f, propietario = null, propietarioReference = null, direccion = "mi direccion", latitud = 4324.4, longitud = 4234.4,id = "4234"),
            Garaje(moto = true, largo = 34.0f, ancho = 434.0f, propietario = null, propietarioReference = null, direccion = "mi direccion", latitud = 4324.4, longitud = 4234.4,id = "43534")
    )

    private val mutexPersonas = Mutex()
    private val mutexGarages = Mutex()

    override suspend fun registerUser (persona : Persona) : Result<String> {
        return withContext(Dispatchers.IO) {
            mutexPersonas.withLock {
                personasList.add(persona)
            }
            Result.success("432423423")

        }
    }

    override suspend fun loginUser (user: String, password: String) : Result<Persona?> {

        return withContext(Dispatchers.IO) {

            var persona : Persona? = null
            mutexPersonas.withLock {
                for (personaAux in personasList) {
                    if (personaAux.username.contentEquals(user) && personaAux.password.contentEquals(
                            password
                        )
                    ) {
                        persona = personaAux
                        break
                    }
                }
            }

            if (persona != null) {
                Result.success(persona)
            } else {
                Result.failure(Throwable("Error"))
            }
        }
    }

    private suspend fun requestPersona (personaReference : DocumentReference?) : Result<Persona> {
        return withContext(Dispatchers.IO) {
            Result.success(Persona())
        }
    }

    override suspend fun registerParking (garaje : Garaje) : Result<String> {
        return withContext(Dispatchers.IO) {
            Result.success("")
        }
    }

    override suspend fun editImageUser(persona: Persona, imageUrl: String): Result<Boolean> {

        return withContext(Dispatchers.IO) {
            Result.success(true)
        }

    }

    override suspend fun allParking(propietario: Persona?): Result<List<Garaje>> {
        return withContext(Dispatchers.IO) {
            Result.success(listOf())
        }
    }

    override suspend fun deleteParking (garaje: Garaje) : Result<Boolean> {
        return withContext(Dispatchers.IO) {
            Result.success(true)
        }
    }

    override suspend fun updateParking(
        direccion: String, longitud : Double, latitud : Double, largo: Float, ancho: Float, moto: Boolean
                                        ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            Result.success(true)
        }
    }

    override suspend fun updatePropietario(garage : Garaje, persona: Persona) {
        return withContext(Dispatchers.IO) {
            Result.success(true)
        }
    }

    override suspend fun getGarageNear(locationObject: Location, radio : Int): Result<Garaje?> {
        return withContext((Dispatchers.IO)) {
            Result.success(null)
        }
    }

}
