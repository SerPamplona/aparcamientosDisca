package com.sergiop.aparcamientos.storage

import android.net.Uri
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona

interface DataStorage {
    suspend fun uploadImage (imageUri : Uri) : Result<String>
}