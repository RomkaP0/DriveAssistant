package com.romka_po.driveassistant.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.databinding.FragmentRegisterBinding
import com.romka_po.driveassistant.repositories.FBRepository

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = FBRepository(FBTools())
        val viewModelProviderFactory = RegisterViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(RegisterViewModel::class.java)
        registerObservers()
        binding.apply {
            btnSignin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_navigation_login)
            }
            registrate.setOnClickListener {
                viewModel.signUpUser(email.text.toString(), password.text.toString(), password.text.toString())
            }
        }

    }
    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                findNavController().navigate(R.id.action_registerFragment_to_navigation_dashboard)
            }
        }
    }
    
}