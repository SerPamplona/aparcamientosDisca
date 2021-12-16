package com.sergiop.aparcamientos.ui.activitys.home.screens.listParking

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.ui.activitys.details.DetailsGarageActivity
import com.sergiop.aparcamientos.ui.activitys.home.screens.allListAllParking.AllListParkingState

@ExperimentalMaterialApi
@Composable
fun AllParkingPage(mContext : Context,
                   allParkingViewModel: AllListParkingViewModel = hiltViewModel()) {

    val stateAllListParking = allParkingViewModel.listParkingState.value

    showListAllParking(
        stateAllListParking,
        onRefresh = {
            allParkingViewModel.requestListParking()
        },
        detailsClickListener = { garage ->
            val intent = Intent(mContext, DetailsGarageActivity::class.java).apply {
                putExtra("garage", garage)
            }
            mContext.startActivity(intent)
        }
    )

    if ((! stateAllListParking.loadingList) && (stateAllListParking.list.isNullOrEmpty())) {
        emptyListParking()
    }

    if (stateAllListParking.loadingList) {
        allListParkingLoading()
    }
}

@ExperimentalMaterialApi
@Composable
fun showListAllParking (    stateList : AllListParkingState,
                            onRefresh : () -> Unit,
                            detailsClickListener: (Garaje) -> Unit) {

    SwipeRefresh(
        state = rememberSwipeRefreshState(stateList.loadingList),
        onRefresh = onRefresh,
    ) {
        LazyColumn {
            items(count = stateList.list?.size?: 0) { index ->
                val garage = stateList.list?.get(index)?: Garaje()
                itemParking(garage, index, detailsClickListener)
            }
        }
    }
}

@Composable
private fun allListParkingLoading () {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun emptyAllListParking() {
    Text("NO EXISTEN PARKING")
}

@ExperimentalMaterialApi
@Composable
fun itemParking (parking : Garaje,
                 index : Int,
                 detailsClickListener: ((Garaje) -> Unit)) {

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

        }

    }


}