package com.romka_po.driveassistant.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.romka_po.driveassistant.adapters.CheckRVAdapter
import com.romka_po.driveassistant.databinding.FragmentDashboardBinding
import com.romka_po.driveassistant.model.Check
import com.romka_po.driveassistant.utils.CheckIcon

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var checkRVAdapter: CheckRVAdapter
    lateinit var checkIcon: CheckIcon
    lateinit var mapView: MapView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        mapView = binding.mapView
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        checkIcon = CheckIcon()
        val list = mutableListOf<Check>()
        for (i in 0..13){
            list.add(Check("Engine", checkIcon.getCheckIcon("Engine"),0.0,40000.0))
        }
        checkRVAdapter.differ.submitList(list)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupRecyclerView(){
        checkRVAdapter = CheckRVAdapter()

        binding.rvcheck.apply {
            adapter = checkRVAdapter
            layoutManager = GridLayoutManager(context, 2)

        }
    }



}