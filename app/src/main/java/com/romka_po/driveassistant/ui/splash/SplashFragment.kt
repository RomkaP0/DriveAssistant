package com.romka_po.driveassistant.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.adapters.VKTools
import com.romka_po.driveassistant.databinding.FragmentSplashBinding
import com.romka_po.driveassistant.repositories.FBRepository


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    lateinit var viewModel: SplashViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = FBRepository(FBTools(), VKTools())
        val viewModelProviderFactory = SplashViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SplashViewModel::class.java)
        registerObservers()
        getUser()
    }
    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user!=null) {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_dashboard)
            }
            else{
                findNavController().navigate(R.id.action_splashFragment_to_navigation_login)

            }
        }
    }
    fun getUser(){
        viewModel.getUser()
    }

}