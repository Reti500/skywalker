package com.ricardgo.android.skywalker.models

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ricardgo.android.skywalker.db.AppDatabase
import com.ricardgo.android.skywalker.db.dao.RouteDAO
import com.ricardgo.android.skywalker.db.entity.RouteEntity

class Route(context: Context) {

    private val routeDAO: RouteDAO? = AppDatabase.getInstance(context)?.routeDao()
    val entity: RouteEntity? = null

    fun insert(route: RouteEntity) {
        routeDAO?.also {
            ExecAsyncTask(routeDAO).execute(route)
        }
    }

    fun all() : LiveData<List<RouteEntity>> {
        return routeDAO?.getAll() ?: MutableLiveData<List<RouteEntity>>()
    }

    private class ExecAsyncTask(private var dao: RouteDAO) : AsyncTask<RouteEntity, Void, Void>() {
        override fun doInBackground(vararg routes: RouteEntity?): Void? {
            for (r in routes) {
                if (r != null) dao.insert(r)
            }

            return null
        }
    }
}