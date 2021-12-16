package com.sergiop.aparcamientos.ui.activitys.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sergiop.aparcamientos.model.Persona
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import io.grpc.InternalChannelz.id

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home")
    object AllParkings : DrawerScreens("Todos Parking", "allParkings")
    object RegisterParking : DrawerScreens("Registrar Parking", "registerParking")
    object YourParkings : DrawerScreens("Tus Parking", "yourParkings")
    object Help : DrawerScreens("Help", "help")
}

@Composable
fun Drawer(
    personaLogged : Persona,
    screens : List<DrawerScreens>,
    modifier: Modifier = Modifier,
    editPhoto: () -> Unit,
    logoutClick: () -> Unit,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Box {
            GlideImage(
                imageModel = personaLogged.imagen,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularReveal = CircularReveal(duration = 250),
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Icon(Icons.Rounded.Edit, contentDescription = "Editar foto", modifier = Modifier.clickable {
                editPhoto()
            })
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "MENU", color = Color.Black)
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }

        // logout
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Logout",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.clickable {
                logoutClick()
            }
        )
    }
}