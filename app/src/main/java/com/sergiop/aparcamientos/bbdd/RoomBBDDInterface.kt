package com.sergiop.aparcamientos.bbdd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergiop.aparcamientos.bbdd.data.PersonaRoom
import com.sergiop.aparcamientos.bbdd.data.PersonaRoomDao

@Database(entities = arrayOf(PersonaRoom::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaRoomDao
}