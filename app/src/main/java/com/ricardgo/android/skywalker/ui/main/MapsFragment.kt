package com.ricardgo.android.skywalker.ui.main


import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.ricardgo.android.skywalker.MainActivity

import com.ricardgo.android.skywalker.R
import com.ricardgo.android.skywalker.WalkerApplication
import com.ricardgo.android.skywalker.broadcast.LocationReceiver
import com.ricardgo.android.skywalker.databinding.FragmentMapsBinding
import com.ricardgo.android.skywalker.db.entity.Point
import com.ricardgo.android.skywalker.services.LocationService
import kotlinx.android.synthetic.main.fragment_maps.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment(), LocationReceiver.OnLatLngChange, OnMapReadyCallback {

    private lateinit var binding : FragmentMapsBinding
    private lateinit var viewModel: MainViewModel
    private val locationPermissions = 101
    private var receiver = LocationReceiver()
    private var mMap: GoogleMap? = null

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

        activity?.let { fActivity ->
            viewModel = ViewModelProviders.of(fActivity).get(MainViewModel::class.java)
            viewModel.getRecordingState().observe(fActivity, Observer { isRecording ->
                startRecordLocation(isRecording)
            })

            binding.viewModel = viewModel
            receiver.listener = this

            startMap()
        }
    }

    override fun onResume() {
        super.onResume()

        activity?.also {
            it.registerReceiver(receiver, IntentFilter(LocationService.receiver))
        }
    }

    override fun latLngChange(p: Point) {
        mMap?.isMyLocationEnabled = true
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(p.latitude, p.longitude), 16.0f))
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0

        requestLocationPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            locationPermissions -> {
                Log.e("Map", "Permission OK")
                var ok = true
                grantResults.forEach { r -> if (r != PackageManager.PERMISSION_GRANTED) ok = false }

                if (ok) {
                    // Permission granted, start to record location
                    //startRecordLocation(viewModel.getRecordingState().value == true)
                    Log.e("Map", "Start service")
                    activity!!.startService(Intent(activity!!, LocationService::class.java).apply {
                        this.putExtra(LocationService.routeIdExtra, UUID.randomUUID().toString())
                    })
                } else {
                    Toast.makeText(context!!, getString(R.string.need_location_permission), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun manageRecording(isRecording: Boolean) {
        val imgRes = if (isRecording)
            R.drawable.ic_stop_black_24dp else R.drawable.ic_add_black_24dp

        val strRes = if (isRecording)
            getString(R.string.stop_route) else getString(R.string.start_route)

        maps_action_image.setImageResource(imgRes)
        maps_action_text.text = strRes
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissions
        )
    }

    private fun startRecordLocation(isRecording: Boolean) {
        WalkerApplication.prefs.trackLocation = isRecording
        manageRecording(isRecording)
    }
}
