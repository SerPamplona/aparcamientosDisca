package com.sergiop.aparcamientos.data

import android.location.Location
import com.sergiop.aparcamientos.bbdd.BBDD
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BBDDRepository @Inject constructor(val bbdd : BBDD) : DataRepository {

    override suspend fun registerUser (persona : Persona) : Result<String> {
        bbdd.insertarPersona(persona)
        return Result.success(persona.username)
    }

    override suspend fun loginUser (user: String, password: String) : Result<Persona?> {
        return Result.success(bbdd.buscarPersona(username = user, password = password))
    }

    override suspend fun registerParking(garaje: Garaje): Result<String> {
        return withContext(Dispatchers.IO) {
            delay(5000)
            Result.success("32544535435")
        }
    }

    override suspend fun editImageUser(persona: Persona, imageUrl: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun allParking(propietario: Persona?): Result<List<Garaje>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteParking(garaje: Garaje): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateParking(
        direccion: String, longitud : Double, latitud : Double, largo: Float, ancho: Float, moto: Boolean
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePropietario(garaje: Garaje, persona: Persona) {
        TODO("Not yet implemented")
    }

    override suspend fun getGarageNear(locationObject: Location, radio : Int): Result<Garaje?> {
        TODO("Not yet implemented")
    }

}