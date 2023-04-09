package com.romka_po.driveassistant.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.adapters.ModelRVAdapter
import com.romka_po.driveassistant.databinding.FragmentHomeBinding
import com.romka_po.driveassistant.factories.ViewModelProviderFactory
import com.romka_po.driveassistant.model.Resource
import com.romka_po.driveassistant.repositories.AutoAPIRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var bundle:Bundle = Bundle()
    lateinit var viewModel: HomeViewModel
    private lateinit var modelRVAdapter: ModelRVAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = AutoAPIRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.getBrands()
        viewModel.brands.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
//                    hideProgressBar()
                    val data = response.data
                    bundle.putParcelableArray("helo", data?.toTypedArray())

                }

                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let { message ->
//                        Log.e(TAG, message)
                        Toast.makeText(context, "This BIN isn`t exist", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        })
        viewModel.models.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
//                    hideProgressBar()
                    val data = response.data
                    modelRVAdapter.differ.submitList(data)
//                    textView.text = data!![0].name
//                    response.data.forEach {Log.i("Marks", it.name )  }

//                    checkAll(data!!)
                }

                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let { message ->
//                        Log.e(TAG, message)
                        Toast.makeText(context, "This BIN isn`t exist", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        })







        binding.brand.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_dialogWithData)
        }
        binding.brand.setOnClickListener {
            if (bundle.containsKey("helo"))
                findNavController().navigate(R.id.action_navigation_home_to_dialogWithData, bundle)
            else
                Toast.makeText(context, "No bundle", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val key = arguments?.getString("key", null)
        var job: Job? = null
        key?.let {
            binding.brand.setText(key)
            job?.cancel()
            job = MainScope().launch { viewModel.getModels(it) }

        }

    }
    fun setupRecyclerView(){
        modelRVAdapter = ModelRVAdapter()

        binding.rvModel.apply {
            adapter = modelRVAdapter
            layoutManager = LinearLayoutManager(context)

        }

    }
}