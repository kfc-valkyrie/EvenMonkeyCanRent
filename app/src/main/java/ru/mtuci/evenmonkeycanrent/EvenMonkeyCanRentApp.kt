package ru.mtuci.evenmonkeycanrent

import android.app.Application
import ru.mtuci.evenmonkeycanrent.di.AppContainer

class EvenMonkeyCanRentApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
