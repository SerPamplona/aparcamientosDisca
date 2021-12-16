package com.sergiop.aparcamientos.services

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.*
import com.sergiop.aparcamientos.activity.COUNT_KEY
import com.sergiop.aparcamientos.activity.NEAR_GARAGE_KEY
import com.sergiop.aparcamientos.activity.USER_KEY
import com.sergiop.aparcamientos.settings.data.UserSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "DataLayerSample"

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var userSetting: UserSettings

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDataChanged: $dataEvents")
        }

        // Loop through the events and send a message
        // to the node that created the data item.
        dataEvents  .map { it.dataItem.uri }
                    .forEach { uri ->
                        if (uri.path?.compareTo(LOGIN_OPERATION) == 0) {
                            val putDataReq: PutDataRequest = PutDataMapRequest.create(
                                LOGIN_OPERATION_RESPONSE).run {
                                dataMap.putString(USER_KEY, userSetting.username)
                                dataMap.putLong("Time", System.currentTimeMillis())
                                asPutDataRequest()
                            }

                            val putDataTask: Task<DataItem> = Wearable.getDataClient(this).putDataItem(putDataReq)
                            putDataTask.addOnSuccessListener {
                                Log.v("DataLayerListenerService","Dato enviado desde el teléfono")
                            }
                        } else if (uri.path?.compareTo(NEAR_GARAGE_OPERATION) == 0) {
                            val putDataReq: PutDataRequest = PutDataMapRequest.create(
                                NEAR_GARAGE_OPERATION_RESPONSE).run {
                                dataMap.putString(NEAR_GARAGE_KEY, "Plaza de Zaragoza")
                                dataMap.putLong("Time", System.currentTimeMillis())
                                asPutDataRequest()
                            }

                            val putDataTask: Task<DataItem> = Wearable.getDataClient(this).putDataItem(putDataReq)
                            putDataTask.addOnSuccessListener {
                                Log.v("DataLayerListenerService","Dato enviado desde el teléfono")
                            }
                        }
                    }
    }
}