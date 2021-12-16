package com.sergiop.aparcamientos.data

import android.app.Person
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.sergiop.aparcamientos.USER_KEY
import com.sergiop.aparcamientos.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WearRealRepository(private val wearClient : DataClient) : DataRepository {

    override suspend fun login(): Result<Boolean> {

        return withContext(Dispatchers.IO) {

            val dataRequest: PutDataRequest = PutDataMapRequest.create(LOGIN_OPERATION).run {
                dataMap.putLong("time", System.currentTimeMillis());
                asPutDataRequest()
            }
            val taskRequest = wearClient.putDataItem(dataRequest)
            val dataItemResult = taskRequest.await()

            if (dataItemResult.isDataValid) {
                Result.success(true)
            } else {
                Result.failure(Throwable("Por favor, logueate en el dispositivo"))
            }

        }

    }

    override suspend fun nearGarage(): Result<Boolean> {

        return withContext(Dispatchers.IO) {

            val dataRequest: PutDataRequest = PutDataMapRequest.create(NEAR_GARAGE_OPERATION).run {
                dataMap.putLong("time", System.currentTimeMillis());
                asPutDataRequest()
            }
            val taskRequest = wearClient.putDataItem(dataRequest)
            val dataItemResult = taskRequest.await()

            if (dataItemResult.isDataValid) {
                Result.success(true)
            } else {
                Result.failure(Throwable("Por favor, logueate en el dispositivo"))
            }
        }

    }


}