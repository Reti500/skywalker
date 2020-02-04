package com.ricardgo.android.skywalker

import android.app.Application
import com.ricardgo.android.skywalker.utils.Prefs

class WalkerApplication : Application() {

    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }
}