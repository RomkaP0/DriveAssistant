package com.romka_po.driveassistant.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.romka_po.driveassistant.adapters.VKTools
import com.romka_po.driveassistant.databinding.FragmentLoginBinding
import com.romka_po.driveassistant.interfaces.GoogleOnSignInStartedListener
import com.romka_po.driveassistant.repositories.FBRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch




class LoginFragment : Fragment() {
    companion object {
        private const val RC_SIGN_IN = 9001
        const val VK_APP_AUTH_CODE = 282
    }

    private var _binding: FragmentLoginBinding? = null
    lateinit var viewModel: LoginViewModel
    private val binding get() = _binding!!

    //    lateinit var googleSignInClient: GoogleSignInClient


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

        val application = this.requireActivity().application
        val repository = FBRepository(FBTools(), VKTools())
        val viewModelProviderFactory =
            LoginViewModelProviderFactory(repository, application, object :
                GoogleOnSignInStartedListener {
                override fun onSignInStarted(client: GoogleSignInClient?) {
                    startActivityForResult(client?.signInIntent, RC_SIGN_IN)
                }
            })
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        registerObservers()



        binding.apply {
            autorize.setOnClickListener {
                viewModel.signInUser(
                    binding.eMail.text.toString(),
                    binding.password.text.toString()
                )
            }
            signingoogle.setOnClickListener {
                viewModel.signIn()
            }
            signinvk.setOnClickListener {
                viewModel.launchVKAuthProvider()
//                viewModel.signInWithVK(requireActivity())
//                VK.login(requireActivity(), arrayListOf(VKScope.EMAIL))

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
        viewModel.vkAuthEmail.observe(viewLifecycleOwner) { token ->
            if (token != "null" && !viewModel.currentUser.isInitialized) {
                MainScope().launch {
                    viewModel.syncVKtoFB()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.d("RequestCode", requestCode.toString())
//        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK && data != null) {
            // this task is responsible for getting ACCOUNT SELECTED
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!

                viewModel.signInWithGoogle(account.idToken!!)

                Toast.makeText(context, "Signed In Successfully", Toast.LENGTH_SHORT)
                    .show()

            } catch (e: ApiException) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }


//            override fun onLoginFailed(errorCode: Int) {
//                // User didn't pass authorization
//            }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Resume", "Resume")
        viewModel.getVKEmail()
    }
}





