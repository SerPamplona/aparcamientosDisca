package com.sergiop.aparcamientos.bbdd.data

import com.sergiop.aparcamientos.model.Persona
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class PersonaBBDD (
    @PrimaryKey
    var username: String = "",
    @Required
    var password: String = "",
    var image : String = "",
    var edad : Int = 0
) : RealmObject() {

    fun toMapPersona () : Persona {
        return Persona(username = username, password = password, imagen = image, edad = edad)
    }

}