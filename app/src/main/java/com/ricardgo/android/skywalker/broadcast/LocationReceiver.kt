package com.ricardgo.android.skywalker.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ricardgo.android.skywalker.db.entity.Point

class LocationReceiver : BroadcastReceiver() {

    var listener: OnLatLngChange? = null

    override fun onReceive(p0: Context?, intent: Intent?) {
        val lat = intent?.getStringExtra("latutide")?.toDouble() ?: 0.0
        val lng = intent?.getStringExtra("longitude")?.toDouble() ?: 0.0

        listener?.latLngChange(Point(lat, lng))
    }

    interface OnLatLngChange {
        fun latLngChange(p: Point)
    }
}