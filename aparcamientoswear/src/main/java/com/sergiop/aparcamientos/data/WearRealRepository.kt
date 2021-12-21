package com.sergiop.aparcamientos.data

import android.location.Location
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WearRealRepository(private val wearClient : DataClient) : DataRepository {

    override suspend fun login(): Result<Boolean> {

        return withContext(Dispatchers.IO) {

            val dataRequest: PutDataRequest = PutDataMapRequest.create(LOGIN_OPERATION).run {
                dataMap.putLong("time", System.currentTimeMillis())
                setUrgent()
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

    override suspend fun nearGarage(location : Location): Result<Boolean> {

        return withContext(Dispatchers.IO) {

            val dataRequest: PutDataRequest = PutDataMapRequest.create(NEAR_GARAGE_OPERATION).run {
                dataMap.putDouble("latitude", location.latitude)
                dataMap.putDouble("longitude", location.longitude)
                dataMap.putLong("time", System.currentTimeMillis())
                asPutDataRequest()
            }
            val taskRequest = wearClient.putDataItem(dataRequest)
            val dataItemResult = taskRequest.await()

            if (dataItemResult.isDataValid) {
                Log.v("WearRealRepository", "Enviado dato NEAR GARAGE")
                Result.success(true)
            } else {
                Result.failure(Throwable("Por favor, logueate en el dispositivo"))
            }
        }

    }


}