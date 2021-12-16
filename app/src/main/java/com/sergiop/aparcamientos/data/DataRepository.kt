package com.sergiop.aparcamientos.data

import android.location.Location
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.flow.MutableStateFlow

interface DataRepository {
    suspend fun registerUser (persona : Persona) : Result<String>
    suspend fun loginUser (user: String, password: String) : Result<Persona?>
    suspend fun registerParking (garaje: Garaje) : Result<String>

    suspend fun editImageUser (persona : Persona, imageUrl : String) : Result<Boolean>

    suspend fun allParking ( propietario : Persona?) : Result<List<Garaje>>

    suspend fun deleteParking ( garaje : Garaje) : Result<Boolean>
    suspend fun updateParking (direccion: String, longitud : Double, latitud : Double, largo: Float, ancho: Float, moto: Boolean) : Result<Boolean>

    suspend fun updatePropietario( garaje : Garaje, persona : Persona)

    suspend fun getGarageNear (actualLocation : Location, radio : Int) : Result<Garaje?>
}