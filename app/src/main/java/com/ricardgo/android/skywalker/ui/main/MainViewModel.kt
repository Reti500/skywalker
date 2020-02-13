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
    private var recording = MutableLiveData<Boolean>()

    var route = Route(WalkerApplication.applicationContext())

    init {
        goToMap()
        recording.value = false
    }

    fun getViewState(): LiveData<MainViewState> = viewState
    fun getRecordingState(): LiveData<Boolean> = recording

    fun goToMap() {
        viewState.value = MainViewState.MAP
    }

    fun recordingAction(v: View) {
        recording.value = recording.value != true
    }
}