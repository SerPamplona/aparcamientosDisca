package com.sergiop.aparcamientos.model

import java.time.LocalDate
import java.time.LocalTime

data class Alquiler (
    val plaza : Garaje, //con el garaje ya se puede saber el propietario
    val arrendatario : Persona,
    val fechaInicio : LocalDate,
    val fechaFinal : LocalDate,
    val horaInicio : LocalTime,
    val horaFinal : LocalTime,
    val puntuacionPropietario : Float,
    val puntuacionInteresado : Float,
    val precio : Float ){

    fun toMap () = mapOf(
        "plaza" to plaza,
        "interesado" to arrendatario,
        "fecha inicio" to fechaInicio,
        "fecha final" to fechaFinal,
        "hora inicio" to horaInicio,
        "hora final" to horaFinal,
        "puntuacion propietario" to puntuacionPropietario,
        "puntuacion interesado" to puntuacionInteresado,
        "precio" to precio
    )
}

