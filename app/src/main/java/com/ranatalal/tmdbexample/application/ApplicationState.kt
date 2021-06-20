package com.ranatalal.tmdbexample.application

import android.app.Application
import com.ranatalal.tmdbexample.helper.uiHelperModule
import com.ranatalal.tmdbexample.networks.repositry.movieRepositryModule
import com.ranatalal.tmdbexample.viewmodel.movieViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationState : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ApplicationState)
            androidLogger(Level.NONE)
            modules(
                listOf(
                   uiHelperModule, movieRepositryModule, movieViewModelModule
                )
            )
        }

    }
}