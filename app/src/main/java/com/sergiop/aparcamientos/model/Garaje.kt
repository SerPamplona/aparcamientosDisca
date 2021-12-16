package com.sergiop.aparcamientos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Garaje(/*var nameGarage : String = "",*/
                  var moto : Boolean = false,
                  var largo : Float = 0.0f,
                  var ancho : Float = 0.0f,
                  var latitud : Double = 0.0,
                  var longitud : Double = 0.0,
                  var propietarioReference : String? = null,
                  var propietario : Persona? = null,
                  var direccion : String = "",
                  var id : String = "") : Parcelable {

    fun toMap () = mapOf(
        "moto" to moto,
        "largo" to largo,
        "ancho" to ancho,
        "latitud" to latitud,
        "longitud" to longitud,
        "direccion" to direccion
    )

}

