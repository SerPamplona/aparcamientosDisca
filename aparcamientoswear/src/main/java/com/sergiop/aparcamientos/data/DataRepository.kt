package com.sergiop.aparcamientos.data

import android.location.Location

interface DataRepository {
    suspend fun login() : Result<Boolean>
    suspend fun nearGarage(location : Location) : Result<Boolean>
}