package com.romka_po.driveassistant.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.databinding.FragmentHomeBinding
import com.romka_po.driveassistant.factories.ViewModelProviderFactory
import com.romka_po.driveassistant.model.Resource
import com.romka_po.driveassistant.repositories.AutoAPIRepository

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var viewModel: HomeViewModel
    lateinit var textView: TextView


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
        viewModel.getBrands()
        viewModel.getModels("VAZ")
        viewModel.brands.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
//                    hideProgressBar()
                    val data = response.data
                    textView.text = data!![0].name
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
        viewModel.models.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
//                    hideProgressBar()
                    val data = response.data
                    textView.text = data!![0].name
                    response.data.forEach {Log.i("Marks", it.name )  }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}