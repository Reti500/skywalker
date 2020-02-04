package com.ricardgo.android.skywalker

import android.app.Application
import android.content.Context
import com.ricardgo.android.skywalker.utils.Prefs

class WalkerApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: WalkerApplication? = null

        lateinit var prefs: Prefs

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)
    }
}