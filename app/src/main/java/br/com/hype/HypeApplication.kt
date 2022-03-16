package br.com.hype

import android.app.Application
import br.com.hype.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HypeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HypeApplication)
            modules(listOf(module))
        }
    }
}