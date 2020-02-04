package com.ricardgo.android.skywalker.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    private val PREFS_NAME = "walker_prefs"
    private val PREFS_TRACKING = "tracking"
    private val mPrefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var trackLocation: Boolean
        get() = mPrefs.getBoolean(PREFS_TRACKING, false)
        set(value) = mPrefs.edit().putBoolean(PREFS_TRACKING, value).apply()
}