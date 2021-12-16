package com.sergiop.aparcamientos.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
/*
    @Provides
    @ActivityScoped
    @Named(LOCAL)
    fun providePersonHombreActivity () : Garaje {
        return Garaje(direccion = "Tirando con local")
    }

    @Provides
    @ActivityScoped
    @Named(FIREBASE)
    fun provideMujerMujerActivity () : Garaje {
        return Garaje (direccion = "Tirando conta firebase")
    }
*/
}