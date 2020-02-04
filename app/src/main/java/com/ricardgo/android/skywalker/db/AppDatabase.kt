package com.ricardgo.android.skywalker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ricardgo.android.skywalker.db.dao.RouteDAO
import com.ricardgo.android.skywalker.db.entity.RouteEntity

@Database(entities = [RouteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun routeDao(): RouteDAO

    companion object {
        private const val dataBaseName = "walkDB"
        private var instance : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            instance ?: synchronized(this) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    dataBaseName
                ).build()
            }

            return instance
        }
    }
}