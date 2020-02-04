package com.ricardgo.android.skywalker.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    enum class MainViewState {
        MAP
    }

    private var viewState = MutableLiveData<MainViewState>()
    private var recordign = MutableLiveData<Boolean>()

    init {
        goToMap()
        recordign.value = false
    }

    fun getViewState(): LiveData<MainViewState> = viewState
    fun getRecordingState(): LiveData<Boolean> = recordign

    fun goToMap() {
        viewState.value = MainViewState.MAP
    }

    fun recordingAction(v: View) {
        if (recordign.value == true) {
            // Is recording, stop and save route
            recordign.value = false
        } else {
            // Is not recording, start to capture route
            recordign.value = true
        }
    }
}