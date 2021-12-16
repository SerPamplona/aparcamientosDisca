package com.sergiop.aparcamientos.di

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.Wearable
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.data.FakeWearRepository
import com.sergiop.aparcamientos.data.WearRealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataRepository (@ApplicationContext appContext : Context) : DataRepository {
        return WearRealRepository(provideDataClient(appContext = appContext))
    }

    @Provides
    @Singleton
    fun provideDataClient (@ApplicationContext appContext : Context) : DataClient {
        return Wearable.getDataClient(appContext)
    }

    @Provides
    @Singleton
    fun providePackageManager (@ApplicationContext appContext : Context) : PackageManager {
        return appContext.packageManager
    }

}