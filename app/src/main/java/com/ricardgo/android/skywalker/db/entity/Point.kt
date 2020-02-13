package com.ricardgo.android.skywalker.db.entity

import androidx.room.ColumnInfo

data class Point (
    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double
)