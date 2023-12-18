package com.example.kotlinfinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.FragmentHomePageBinding
import com.example.kotlinfinal.ui.adapter.BaseCategoriesAdapter
import com.example.kotlinfinal.ui.adapter.ProductAdapter
import com.example.kotlinfinal.ui.viewmodel.HomePageFragmentViewModel


class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel: HomePageFragmentViewModel
    private val navController: NavController by lazy {findNavController()}
    private val bundle: Bundle by lazy {Bundle()}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        binding = FragmentHomePageBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[HomePageFragmentViewModel::class.java]

        viewModel.products.observe(viewLifecycleOwner) { it ->
            binding.rcProducts.adapter = ProductAdapter(it, onClick = {
                  bundle.putString("productId", it.id.toString())
                  val navOptions = NavOptions.Builder().setPopUpTo(R.id.productDetailsFragment, false).build()
                 navController.navigate(
                    R.id.productDetailsFragment,
                    bundle,
                    navOptions
                )
            })
        }

        binding.seeAllCategoriesButton.setOnClickListener {
           navController.navigate(R.id.categroiesFragment)
        }

        viewModel.categories.observe(viewLifecycleOwner) { it ->
        binding.rcCategories.adapter = BaseCategoriesAdapter(it, onClick = {
            viewModel.getProductWithCategoriesName(it)
        }, R.layout.layout_categories_horizontal)
    }

        return binding.root

    }

}