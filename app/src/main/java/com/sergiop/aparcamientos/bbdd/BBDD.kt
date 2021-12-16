package com.sergiop.aparcamientos.bbdd

import com.sergiop.aparcamientos.model.Persona

interface BBDD {
    suspend fun insertarPersona (persona : Persona)
    suspend fun insertarListPersona(listPersonas : List<Persona>)
    suspend fun buscarPersona (username : String, password : String) : Persona?
}