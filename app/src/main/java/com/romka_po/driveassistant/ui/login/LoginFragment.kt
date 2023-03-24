package com.romka_po.driveassistant.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.databinding.FragmentLoginBinding
import com.romka_po.driveassistant.repositories.FBRepository

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    lateinit var viewModel: LoginViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val repository = FBRepository(FBTools())
        val viewModelProviderFactory = LoginViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)


        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        registerObservers()

        val root: View = binding.root

        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signin.setOnClickListener {
                viewModel.signInUser("roman.kradyk@gmail.com", "261002")
            }
        }

    }





    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner, { user ->
            user?.let {
                findNavController().navigate(R.id.action_navigation_login_to_navigation_dashboard)
            }
        })
    }

}