package com.sergiop.aparcamientos.ui.activitys.listparking

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.*
import com.sergiop.aparcamientos.USER_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListParkingActivity : ComponentActivity(), DataClient.OnDataChangedListener {

    var listaTextos : SnapshotStateList<List<String>>? = null

    @ExperimentalWearMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            listaTextos = remember {
                mutableStateListOf(listOf("Lista vacia"))
            }

            MaterialTheme {
                val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()

                Scaffold(
                    timeText = {
                        TimeText()
                    },
                    vignette = {
                        Vignette(vignettePosition = VignettePosition.TopAndBottom)
                    },
                    positionIndicator = {
                        PositionIndicator(
                            scalingLazyListState = scalingLazyListState
                        )

                    }
                ) {
                    Box{

                            ScalingLazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    top = 28.dp,
                                    start = 10.dp,
                                    end = 10.dp,
                                    bottom = 40.dp
                                ),
                                verticalArrangement = Arrangement.Center,
                                state = scalingLazyListState
                            ) {


                                items(listaTextos?.size?:0) { index ->
                                    val stringToView = listaTextos?.get(index)
                                    Chip(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 10.dp),
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = android.R.drawable.btn_star_big_on),
                                                contentDescription = "Star",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .wrapContentSize(align = Alignment.Center),
                                            )
                                        },
                                        label = {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                color = MaterialTheme.colors.onPrimary,
                                                text = "Item ${stringToView}"
                                            )
                                        },
                                        onClick = {
                                        }
                                    )
                                }
                            }

                        Button(onClick = { recuperarListaParking() }) {
                            Text("Recargar lista parkings")
                        }
                    }
                }
            }
        }
    }

    private fun recuperarListaParking() {
        val putDataReq: PutDataRequest = PutDataMapRequest.create("/start-activity").run {
            dataMap.putLong("Time",System.currentTimeMillis());
            asPutDataRequest()
        }
        val putDataTask: Task<DataItem> = Wearable.getDataClient(this).putDataItem(putDataReq)
        putDataTask.addOnSuccessListener {
            Log.v("ListParkingActivity", "Dato enviado correctamente")
        }
    }

    override fun onResume() {
        super.onResume()
        Wearable.getDataClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()
        //Wearable.getDataClient(this).removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            // DataItem changed
            if (event.type == DataEvent.TYPE_CHANGED) {
                event.dataItem.also { item ->
                    if (item.uri.path?.compareTo("/response-user") == 0) {
                        DataMapItem.fromDataItem(item).dataMap.apply {
                            listaTextos?.clear()
                            val userToLogin = this.get<String>(USER_KEY)
                            listaTextos?.add(listOf("Usuario auth: ${userToLogin}"))

                        }
                    }
                }
            } else if (event.type == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }
}