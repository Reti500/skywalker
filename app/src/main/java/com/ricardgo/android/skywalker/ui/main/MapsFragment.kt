package com.ricardgo.android.skywalker.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.ricardgo.android.skywalker.R
import com.ricardgo.android.skywalker.databinding.FragmentMapsBinding
import kotlinx.android.synthetic.main.fragment_maps.*

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment : Fragment() {

    private lateinit var binding : FragmentMapsBinding
    private lateinit var viewModel: MainViewModel

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
                manageRecording(isRecording)
            })

            binding.viewModel = viewModel
        }
    }

    private fun manageRecording(isRecording: Boolean) {
        val imgRes = if (isRecording)
            R.drawable.ic_stop_black_24dp else R.drawable.ic_add_black_24dp

        val strRes = if (isRecording)
            getString(R.string.stop_route) else getString(R.string.start_route)

        maps_action_image.setImageResource(imgRes)
        maps_action_text.text = strRes
    }
}
