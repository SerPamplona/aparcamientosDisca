package com.sergiop.aparcamientos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AparcamientoWearApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}