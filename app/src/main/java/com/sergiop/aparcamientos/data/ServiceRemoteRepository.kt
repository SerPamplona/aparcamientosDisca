package com.sergiop.aparcamientos.data

import android.location.Location
import android.util.Log
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONTokener
import javax.inject.Inject

class ServiceRemoteRepository @Inject constructor(private val httpClient : OkHttpClient) : DataRepository {

    override suspend fun registerUser (persona : Persona) : Result<String> {
        return withContext(Dispatchers.IO) {
            Result.success("")
        }
    }

    override suspend fun loginUser (user: String, pass: String) : Result<Persona?> {

        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("https://firebasestorage.googleapis.com/v0/b/aparcamientodisponible.appspot.com/o/documents%2Fpersonas.json?alt=media&token=531be96b-dcb9-487d-b64f-fa41e7d013e6")
                .build()

            val response = httpClient.newCall(request).execute()

            if (response.isSuccessful) {
                val body = response?.body?.string()
                Log.v("JSONRemote", body?: "")

                var loginPersona : Persona? = null
                val jsonArray = JSONTokener(body).nextValue() as JSONArray
                for (i in 0 until jsonArray.length()) {
                    // ID
                    val username = jsonArray.getJSONObject(i).getString("username")
                    val password = jsonArray.getJSONObject(i).getString("password")
                    val image = jsonArray.getJSONObject(i).getString("image")

                    if (username.equals(user) && password.equals(pass)) {
                        loginPersona = Persona(username = username, password = password, imagen = image)
                        break
                    }
                }

                if (loginPersona != null) {
                    Result.success(loginPersona)
                } else {
                    Result.failure(Throwable("Error login"))
                }

            } else {
                Result.failure(Throwable("Error"))
            }
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
            val list = mutableListOf<Garaje>()

            Result.success(list)
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

    override suspend fun updatePropietario(garaje: Garaje, persona: Persona) {
        TODO("Not yet implemented")
    }

    override suspend fun getGarageNear(actualLocation: Location, radio : Int): Result<Garaje?> {
        TODO("Not yet implemented")
    }

}
