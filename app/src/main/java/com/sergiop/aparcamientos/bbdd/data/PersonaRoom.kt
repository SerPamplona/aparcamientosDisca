package com.sergiop.aparcamientos.bbdd.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonaRoom(
    @PrimaryKey
    var username: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "image")
    var image : String = "",

    @ColumnInfo(name = "edad")
    var edad : Int = 0
)