package com.sergiop.aparcamientos.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeWearRepository : DataRepository {

    override suspend fun login(): Result<Boolean> {

        return withContext(Dispatchers.IO) {
            delay(5000)
            val number = (0..10).random()

            if (number % 2 == 0) {
                Result.success(true)
            } else {
                if (number > 5) {
                    Result.failure(Throwable("Error imposible conectar con el reloj"))
                } else {
                    Result.failure(Throwable("no esta logueado en el reloj"))
                }
            }
        }

    }

    override suspend fun nearGarage(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            Result.success(true)
        }
    }

}