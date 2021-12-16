package com.sergiop.aparcamientos.di

import com.sergiop.aparcamientos.activity.FIREBASE
import com.sergiop.aparcamientos.activity.LOCAL
import com.sergiop.aparcamientos.model.Garaje
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Named

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