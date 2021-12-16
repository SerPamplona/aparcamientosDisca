package com.sergiop.aparcamientos.bbdd

import com.sergiop.aparcamientos.bbdd.data.PersonaRoom
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomBBDD (val bbdd : AppDatabase): BBDD {

    override suspend fun insertarPersona(persona : Persona) {
        return withContext(Dispatchers.IO){
            bbdd.personaDao().insertPersona(PersonaRoom(username = persona.username,
                password = persona.password,
                image = persona.imagen))
        }
    }

    override suspend fun insertarListPersona(listPersonas : List<Persona>) {
        TODO("Not yet implemented")
    }

    override suspend fun buscarPersona(username: String, password: String): Persona? {
        return withContext(Dispatchers.IO){
            val personRoom = bbdd.personaDao().findByUsernamePasswrod(username = username, password = password)
            Persona(username = personRoom?.username?: "", password = personRoom?.password?:"no", imagen = personRoom?.image?:"no")
        }
    }
}