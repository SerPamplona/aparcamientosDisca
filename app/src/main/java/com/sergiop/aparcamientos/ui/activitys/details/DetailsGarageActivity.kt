package com.sergiop.aparcamientos.ui.activitys.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.sergiop.aparcamientos.activity.ENVIROMENT
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.ui.theme.AparcamientosTheme
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class DetailsGarageActivity : ComponentActivity() {

    private val detailsGarageViewModel : DetailsViewModel by viewModels()

    @Inject
    lateinit var userLogged : Persona

    lateinit var garaje : Garaje

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val garajeExtra = intent.extras?.getParcelable<Garaje>("garage")
        setContent {
            AparcamientosTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box {
                        mapViewDetails(garage = garajeExtra!!)
                        cardDetailsGarage(garage = garajeExtra!!, ownerClick = {
                            detailsGarageViewModel.updatePropietarioParking(garajeExtra, userLogged)
                        })
                    }
                }
            }
        }
    }

    @Composable
    fun cardDetailsGarage (garage : Garaje,
                           ownerClick : () -> Unit) {
        Card(   elevation = 4.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
            Column (modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()){
                Row {
                    GlideImage(
                        imageModel = garage.propietario?.imagen ?: "",
                        // Crop, Fit, Inside, FillHeight, FillWidth, None
                        contentScale = ContentScale.Crop,
                        // shows an image with a circular revealed animation.
                        circularReveal = CircularReveal(duration = 250),
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(11.dp))

                    Button(onClick = ownerClick,
                            enabled = garage.propietario?.equals(userLogged)?:true) {
                        Text(text = "Ser propietario")
                    }
                }
                Spacer(modifier = Modifier.height(9.dp))
                Text("Garage ${garage.direccion}")
                Spacer(modifier = Modifier.height(9.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text(" largo del garage ${garage.largo} por, ancho del garage ${garage.ancho}")
                Spacer(modifier = Modifier.height(6.dp))
                if(garage.moto == true)
                    Text(text = "este garaje es para moto")
                else
                    Text(text = "este garaje es para coche")
            }
        }
    }

    @Composable
    fun mapViewDetails (garage : Garaje) {
        val mapView = rememberMapViewWithLifecycle()

        AndroidView({mapView}, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {mapView ->
             mapView.getMapAsync { map ->

                 map.uiSettings.isZoomControlsEnabled = true

                 val location =  LatLng(garage.latitud, garage.longitud)
                 map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,18f))
                 val markerOptions = MarkerOptions()
                                         .title("Mi aparcamiento")
                                         .position(location)

                 map.addMarker(markerOptions)
             }
        }
    }
}
