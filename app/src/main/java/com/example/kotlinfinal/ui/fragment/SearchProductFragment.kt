package com.example.kotlinfinal.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.LayoutSearchbarViewBinding
import com.example.kotlinfinal.helper.Utils.hideNavigationBarAndAppbar
import com.example.kotlinfinal.ui.adapter.ProductAdapter
import com.example.kotlinfinal.ui.viewmodel.SearchProductFragmentViewModel

class SearchProductFragment : Fragment() {
    private lateinit var binding: LayoutSearchbarViewBinding
    private lateinit var viewModel: SearchProductFragmentViewModel
    private val navController: NavController by lazy {findNavController()}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        hideNavigationBarAndAppbar(this)
        binding = LayoutSearchbarViewBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[SearchProductFragmentViewModel::class.java]

        binding.searchView.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchProduct(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        viewModel.products.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ProductAdapter(it, onClick = {
                val bundle = Bundle()
                bundle.putString("productId", it.id.toString())
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.productDetailsFragment, false).build()
                navController.navigate(
                    R.id.productDetailsFragment,
                    bundle,
                    navOptions
                )
            })
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideNavigationBarAndAppbar(this, false, false)
    }

}