package com.gorkemarslanbogan.ecommer1234.ui

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
import com.example.kotlinfinal.databinding.FragmentCategroiesBinding
import com.example.kotlinfinal.ui.adapter.BaseCategoriesAdapter
import com.example.kotlinfinal.ui.viewmodel.CategoriesFragmentViewModel


class CategroiesFragment : Fragment() {
    private lateinit var binding: FragmentCategroiesBinding
    private lateinit var viewModel: CategoriesFragmentViewModel
    private val navController: NavController by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategroiesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CategoriesFragmentViewModel::class.java]

        viewModel.categories.observe(viewLifecycleOwner){ it ->
             it.remove("All")
            binding.rcCategories.adapter = BaseCategoriesAdapter(it, onClick = {
                val bundle = Bundle()
                bundle.putString("categoriesName", it)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.favoriesFragment, false).build()
                navController.navigate(
                    R.id.favoriesFragment,
                    bundle,
                    navOptions
                )
            }, R.layout.layout_categories_horizontal2)
        }

        return binding.root
    }

}