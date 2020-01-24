package com.ricardgo.android.skywalker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    enum class MainViewState {
        MAP
    }

    private var viewState = MutableLiveData<MainViewState>()

    init {
        goToMap()
    }

    fun getViewState(): LiveData<MainViewState> = viewState

    fun goToMap() {
        viewState.value = MainViewState.MAP
    }
}