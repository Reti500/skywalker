package com.ricardgo.android.skywalker.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricardgo.android.skywalker.WalkerApplication
import com.ricardgo.android.skywalker.models.Route

class MainViewModel : ViewModel() {

    enum class MainViewState {
        MAP
    }

    private var viewState = MutableLiveData<MainViewState>()
    private var recordign = MutableLiveData<Boolean>()

    var route = Route(WalkerApplication.applicationContext())

    init {
        goToMap()
    }

    fun getViewState(): LiveData<MainViewState> = viewState
    fun getRecordingState(): LiveData<Boolean> = recordign

    fun goToMap() {
        viewState.value = MainViewState.MAP
    }

    fun recordingAction(v: View) {
        recordign.value = recordign.value != true
    }
}