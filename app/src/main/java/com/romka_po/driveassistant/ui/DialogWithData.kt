package com.romka_po.driveassistant.ui

import com.romka_po.driveassistant.adapters.rvadapters.BrandRVAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.databinding.DialogBrandBinding
import com.romka_po.driveassistant.model.Brand

class DialogWithData : DialogFragment() {

    companion object {

        const val TAG = "DialogWithData"

    }
    private lateinit var brandRVAdapter: BrandRVAdapter
    private var _binding: DialogBrandBinding? = null
    val bundle = Bundle()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBrandBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog);


        return root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            dialog?.dismiss()
        }
        setupRecyclerView()

        brandRVAdapter.setOnItemClickListener {
            bundle.putString("key", it.id)
            Toast.makeText(context, it.id, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_dialogWithData_to_navigation_home, bundle)
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
//
        }

    }

    private fun setupRecyclerView(){
        val arg = requireArguments().getParcelableArray("helo") as Array<*>
        Log.d("Size", arg.size.toString())
        brandRVAdapter = BrandRVAdapter(
            arg as Array<Brand>
        )
        val itemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

        binding.rvBrand.apply {
            adapter = brandRVAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoration)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}