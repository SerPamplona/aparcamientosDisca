package com.sergiop.aparcamientos.bbdd.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonaRoomDao {
    @Query("SELECT * FROM PersonaRoom")
    fun getAll(): List<PersonaRoom>

    @Query("SELECT * FROM PersonaRoom WHERE username LIKE :username AND " +
            "password LIKE :password LIMIT 1")
    fun findByUsernamePasswrod(username: String, password: String): PersonaRoom?

    @Insert
    fun insertPersona(persona: PersonaRoom)
}