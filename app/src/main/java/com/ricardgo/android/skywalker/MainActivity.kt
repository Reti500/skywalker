package com.ricardgo.android.skywalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ricardgo.android.skywalker.databinding.ActivityMainBinding
import com.ricardgo.android.skywalker.ui.main.MainViewModel
import com.ricardgo.android.skywalker.ui.main.MapsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Observers
        viewModel.getViewState().observe(this, Observer { changeFragment(it) })
    }

    private fun changeFragment(state: MainViewModel.MainViewState) {
        val f = when(state) {
            MainViewModel.MainViewState.MAP -> MapsFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, f).commit()
    }
}
