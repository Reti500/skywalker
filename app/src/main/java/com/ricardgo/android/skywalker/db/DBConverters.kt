package com.ricardgo.android.skywalker.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ricardgo.android.skywalker.db.entity.Point

class DBConverters {
        @TypeConverter
        fun stringToListOfPoints(data : String?) : List<Point> {
            if (data == null) return listOf()

            return Gson().fromJson(data, object : TypeToken<List<Point>>() {}.type)
        }

        @TypeConverter
        fun pointsListToJsonString(l: List<Point>) : String {
            return Gson().toJson(l)
        }
}