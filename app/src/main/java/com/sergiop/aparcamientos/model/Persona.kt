package com.sergiop.aparcamientos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Persona(var username : String = "",
                   var telefono : String = "",
                   var password : String = "",
                   var imagen : String = "",
                   var id : String = "",
                   var edad : Int = 0) : Parcelable {

    fun toMap () = mapOf(   "username" to username,
                            "telefono" to telefono,
                            "password" to password,
                            "imagen" to imagen)

}