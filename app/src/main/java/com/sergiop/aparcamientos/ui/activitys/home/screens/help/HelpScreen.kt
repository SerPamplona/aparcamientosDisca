package com.sergiop.aparcamientos.ui.activitys.home.screens.help

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HelpPage(mContext : Context,
               homeViewModel: HelpViewModel = hiltViewModel()) {

    val scrollState = rememberScrollState()

    //se crea la parte grafica
    Column (modifier = Modifier.verticalScroll( enabled = true, state = scrollState)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("¡¡¡¡ AYUDA !!!!")
        Spacer(modifier = Modifier.height(10.dp))
    }
}