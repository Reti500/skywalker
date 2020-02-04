package com.ricardgo.android.skywalker.db.entity

import androidx.room.ColumnInfo

data class Point (
    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double
)