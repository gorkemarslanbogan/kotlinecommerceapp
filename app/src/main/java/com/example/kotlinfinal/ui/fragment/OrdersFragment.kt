package com.example.kotlinfinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.FragmentFavoriesBinding
import com.example.kotlinfinal.ui.adapter.OrdersAdapter
import com.example.kotlinfinal.ui.viewmodel.OrdersFragmentViewModel


class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentFavoriesBinding
    private lateinit var viewModel: OrdersFragmentViewModel
    private val navController: NavController by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[OrdersFragmentViewModel::class.java]
        binding.title.text = "Orders"
        binding.emptyItemLayout.mainLayout.visibility = View.VISIBLE
        binding.mainLayout.visibility = View.GONE

        binding.emptyItemLayout.navigateButton.setOnClickListener {
            navController.navigate(R.id.homePageFragment)
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                binding.emptyItemLayout.mainLayout.visibility = View.VISIBLE
                binding.mainLayout.visibility = View.GONE
            }else {
                binding.mainLayout.visibility = View.VISIBLE
                binding.emptyItemLayout.mainLayout.visibility = View.GONE
                val linearManager = LinearLayoutManager(requireContext())
                binding.rcProducts.layoutManager = linearManager
                binding.rcProducts.adapter = OrdersAdapter(it)
            }
        }
        return binding.root
    }
}
