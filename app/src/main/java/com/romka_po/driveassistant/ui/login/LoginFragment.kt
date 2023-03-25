package com.romka_po.driveassistant.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.databinding.FragmentLoginBinding
import com.romka_po.driveassistant.repositories.FBRepository
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.exceptions.VKAuthException

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    lateinit var viewModel: LoginViewModel

    private val binding get() = _binding!!
    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = FBRepository(FBTools())
        val viewModelProviderFactory = LoginViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        registerObservers()

        val gso = viewModel.provideGso(requireContext())
        googleSignInClient = viewModel.provideGoogleClient(requireContext(),gso)

        binding.apply {
            autorize.setOnClickListener {
                viewModel.signInUser("roman.kradyk@gmail.com", "261002")
            }
            signingoogle.setOnClickListener {
                signIn()
            }
            btnSignup.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_login_to_registerFragment)
            }
        }
    }


    private fun registerObservers() {
        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                findNavController().navigate(R.id.action_navigation_login_to_navigation_dashboard)
            }
        }
    }


    private fun signIn() {

        val signInIntent: Intent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, 123)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.signInWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
            }

            override fun onLoginFailed(authException: VKAuthException) {

            }

        }
    }


}