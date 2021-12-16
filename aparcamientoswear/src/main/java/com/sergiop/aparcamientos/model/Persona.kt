package com.sergiop.aparcamientos.model

data class Persona(var username : String = "",
                   var telefono : String = "",
                   var password : String = "",
                   var imagen : String = "",
                   var id : String = "",
                   var edad : Int = 0) {

    fun toMap () = mapOf(   "username" to username,
                            "telefono" to telefono,
                            "password" to password,
                            "imagen" to imagen)

}