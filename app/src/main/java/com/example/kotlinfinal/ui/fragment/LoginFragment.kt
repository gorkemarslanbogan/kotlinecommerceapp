package com.example.kotlinfinal.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.FragmentLoginBinding
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.helper.Utils
import com.example.kotlinfinal.helper.Utils.hideNavigationBarAndAppbar
import com.example.kotlinfinal.helper.capitalizeAndReplace
import com.example.kotlinfinal.helper.loadImage
import com.example.kotlinfinal.ui.MainActivity
import com.example.kotlinfinal.ui.viewmodel.LoginFragmentViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel
    private val navController: NavController by lazy {findNavController()}
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        hideNavigationBarAndAppbar(this, true, true)
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[LoginFragmentViewModel::class.java]
        binding.loginButton.setOnClickListener {loginClick()}

        viewModel.jwtUserLiveData.observe(viewLifecycleOwner){
            if(it != null) {
                SharedManagment.saveJwtModel(it)
            }
        }
        return binding.root

    }
    private fun loginClick(){
        lifecycleScope.launch {
            if(Utils.checkEdittext(requireContext(), arrayListOf(binding.filledemailTextInput, binding.filledpasswordTextInput))) {
                if(viewModel.checkLogin(binding.filledemailTextInput.text.toString(), binding.filledpasswordTextInput.text.toString())) {
                    val mainActivity = requireActivity()
                    if(mainActivity is MainActivity){
                        if(viewModel.jwtUser != null){
                            mainActivity.setNavHeader(viewModel.jwtUser!!)
                        }
                    }
                    Utils.showShortToast(requireContext(), requireContext().getString(R.string.str_login_success))
                    navController.navigate(R.id.gotoHomePage)
                } else {
                    Utils.showShortToast(requireContext(), requireContext().getString(R.string.str_user_or_password_wrong))
                }
            } else {
                Utils.showShortToast(requireContext(), requireContext().getString(R.string.str_user_or_password_can_not_empty))
            }
        }
    }

    //fragment kapanırken navigation barın visible yapmak için kullanıyorum burayı
    override fun onDestroyView() {
        super.onDestroyView()
        hideNavigationBarAndAppbar(this,false)
    }

}