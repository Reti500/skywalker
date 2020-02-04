package com.ricardgo.android.skywalker.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ricardgo.android.skywalker.db.DBConverters

@Entity( tableName = RouteEntity.tableName)
@TypeConverters(DBConverters::class)
data class RouteEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "route_id")
    var routeId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "distance")
    val distance: Long,

    @ColumnInfo(name = "start")
    val startTime: Long,

    @ColumnInfo(name = "end")
    val endTime: Long,

    @ColumnInfo(name = "points")
    val points: List<Point>
) {
    companion object {
        const val tableName = "routes"
    }
}