package com.sergiop.aparcamientos.ui.activitys.home.screens.listParking

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.ui.activitys.details.DetailsGarageActivity

@ExperimentalMaterialApi
@Composable
fun listParkingPage(mContext : Context,
                    listParkingViewModel: ListParkingViewModel = hiltViewModel()) {

    val stateListParking = listParkingViewModel.listParkingState.value

    showListaParking(
        stateListParking,
        onRefresh = {
            listParkingViewModel.requestListParking()
        },
        detailsClickListener = { garage ->
            val intent = Intent(mContext, DetailsGarageActivity::class.java).apply {
                putExtra("garage", garage)
            }
            mContext.startActivity(intent)
        },
        deleteClickListener = { garaje , index ->
            listParkingViewModel.deleteParking(garaje = garaje)
            Toast.makeText(mContext, "Eliminando la posición  $index", Toast.LENGTH_LONG).show()
        }
    )

    if ((! stateListParking.loadingList) && (stateListParking.list.isNullOrEmpty())) {
        emptyListParking()
    }

    if (stateListParking.loadingList) {
        listParkingLoading()
    }
}

@Composable
private fun listParkingLoading () {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun emptyListParking() {
    Text("NO EXISTEN PARKING PARA ESTE USUARIO")
}

@ExperimentalMaterialApi
@Composable
fun showListaParking (  stateList : ListParkingState,
                        onRefresh : () -> Unit,
                        detailsClickListener: (Garaje) -> Unit,
                        deleteClickListener : ((Garaje, Int) -> Unit)) {

    SwipeRefresh(
        state = rememberSwipeRefreshState(stateList.loadingList),
        onRefresh = onRefresh,
    ) {
        LazyColumn {
            items(count = stateList.list?.size?: 0) { index ->
                val garage = stateList.list?.get(index)?: Garaje()
                itemParking(garage, index, detailsClickListener, deleteClickListener)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun itemParking (parking : Garaje,
                 index : Int,
                 detailsClickListener: ((Garaje) -> Unit),
                 deleteClickListener : ((Garaje, Int) -> Unit)) {

    Card (elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        onClick = {detailsClickListener(parking)}){
        Column (modifier = Modifier.padding(10.dp)){
            Row {
                Text(text = "Direccion: ")
                Text(text = parking.direccion)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = "Ancho: ")
                Text(text = parking.ancho.toString())
            }
            Row {
                Text(text = "Largo: ")
                Text(text = parking.largo.toString())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = "Latitud: ")
                Text(text = parking.latitud.toString())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = "Longitud: ")
                Text(text = parking.longitud.toString())
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row{
                Button(onClick = {
                    deleteClickListener(parking, index)
                }) {
                    Text("Eliminar plaza")
                }
            }

        }

    }


}