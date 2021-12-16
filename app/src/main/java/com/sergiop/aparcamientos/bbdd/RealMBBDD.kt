package com.sergiop.aparcamientos.bbdd

import android.app.Person
import com.sergiop.aparcamientos.bbdd.data.PersonaBBDD
import com.sergiop.aparcamientos.model.Persona
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealMBBDD (val bbdd : Realm): BBDD {
    override suspend fun insertarPersona(persona : Persona) {
        bbdd.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(PersonaBBDD(username = persona.username, password = persona.password, image = persona.imagen))
        }
    }

    override suspend fun insertarListPersona(listPersonas : List<Persona>) {
        listPersonas.forEach {
            insertarPersona(it)
        }
    }

    override suspend fun buscarPersona(username: String, password: String) : Persona? {
        var loginUser : Persona? = null

        // 2.
        bbdd.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            loginUser = realmTransaction.where(PersonaBBDD::class.java)
                .equalTo("username", username)
                .equalTo("password", password)
                .findFirst()?.toMapPersona()
        }

        return loginUser
    }

}