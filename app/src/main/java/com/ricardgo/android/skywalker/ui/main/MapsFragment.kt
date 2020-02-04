package com.ricardgo.android.skywalker.ui.main


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ricardgo.android.skywalker.MainActivity

import com.ricardgo.android.skywalker.R
import com.ricardgo.android.skywalker.WalkerApplication
import com.ricardgo.android.skywalker.databinding.FragmentMapsBinding
import com.ricardgo.android.skywalker.services.LocationService
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment() {

    private lateinit var binding : FragmentMapsBinding
    private lateinit var viewModel: MainViewModel
    private val locationPermission = 101
    private val fineLocationPermission = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
            viewModel.getRecordingState().observe(this, Observer { isRecording ->
                checkLocationPermission(isRecording)
            })

            binding.viewModel = viewModel
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermission) {

        }

        if (requestCode == fineLocationPermission) {

        }

        when (requestCode) {
            locationPermission -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, start to record location
                    //startRecordLocation(viewModel.getRecordingState().value == true)
                    checkFineLocationPermission(viewModel.getRecordingState().value == true)
                } else {
                    Toast.makeText(context!!, getString(R.string.need_location_permission), Toast.LENGTH_SHORT).show()
                }
            }

            fineLocationPermission -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, start to record location
                    startRecordLocation(viewModel.getRecordingState().value == true)
                } else {
                    Toast.makeText(context!!, getString(R.string.need_location_permission), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun manageRecording(isRecording: Boolean) {
        if (isRecording) {
            context?.also {
                it.startService(Intent(it, LocationService::class.java).apply {
                    this.putExtra(LocationService.routeIdExtra, UUID.randomUUID().toString())
                })
            }
        }

        val imgRes = if (isRecording)
            R.drawable.ic_stop_black_24dp else R.drawable.ic_add_black_24dp

        val strRes = if (isRecording)
            getString(R.string.stop_route) else getString(R.string.start_route)

        maps_action_image.setImageResource(imgRes)
        maps_action_text.text = strRes
    }

    private fun checkLocationPermission(isRecording: Boolean) {
        if (ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Has location permission, start recording route
            //startRecordLocation(isRecording)
            checkFineLocationPermission(isRecording)
        } else {
            // Request permission
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                locationPermission
            )
        }
    }

    private fun checkFineLocationPermission(isRecording: Boolean) {
        if (ContextCompat.checkSelfPermission(activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Has location permission, start recording route
            startRecordLocation(isRecording)
        } else {
            // Request permission
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                fineLocationPermission
            )
        }
    }

    private fun startRecordLocation(isRecording: Boolean) {
        WalkerApplication.prefs.trackLocation = isRecording
        manageRecording(isRecording)
    }
}
