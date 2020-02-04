package com.ricardgo.android.skywalker.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ricardgo.android.skywalker.db.entity.RouteEntity

@Dao
interface RouteDAO {
    @Insert
    fun insert(route: RouteEntity)

    @Update
    fun update(route: RouteEntity)

    @Delete
    fun delete(route: RouteEntity)

    @Query("SELECT * FROM " + RouteEntity.tableName + " ORDER BY start")
    fun getAll(): LiveData<List<RouteEntity>>
}