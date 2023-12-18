package com.example.kotlinfinal.ui.fragment

import android.os.Bundle
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
import com.example.kotlinfinal.databinding.FragmentFavoriesBinding
import com.example.kotlinfinal.helper.capitalizeAndReplace
import com.example.kotlinfinal.ui.adapter.ProductAdapter
import com.example.kotlinfinal.ui.viewmodel.FavroiesFragmentViewModel


class FavoriesFragment : Fragment() {
    private lateinit var binding: FragmentFavoriesBinding
    private lateinit var viewModel: FavroiesFragmentViewModel
    private val navController: NavController by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[FavroiesFragmentViewModel::class.java]

        //fragmentları ortaklaştırdım çünkü aynı ui kullanıyorlar bu sebepte kategoriler sayfasından
        //aynı tasarıma sahip favori sayfasında kategorilermiş gibi listenelicek.
        val receivedBundle = arguments
        if (receivedBundle != null) {
            val value = receivedBundle.getString("categoriesName")
            value.let {
                viewModel.getProductWithCategoriesName(value!!)
                val capitalizeText = it?.capitalizeAndReplace(it)
                binding.title.text = capitalizeText
            }
        }
        ////


        viewModel.products.observe(viewLifecycleOwner) { it ->
            binding.rcProducts.adapter = ProductAdapter(it, onClick = {
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
}
