package com.example.kotlinfinal.ui.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kotlinfinal.R
import com.example.kotlinfinal.databinding.FragmentCardsBinding
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.helper.Utils.hideAppbarHomeIcon
import com.example.kotlinfinal.helper.Utils.hideNavigationBarAndAppbar
import com.example.kotlinfinal.helper.Utils.showShortToast
import com.example.kotlinfinal.ui.MainActivity
import com.example.kotlinfinal.ui.adapter.CardsAdapter
import com.example.kotlinfinal.ui.viewmodel.CardsFragmentViewModel

class CardsFragment : Fragment() {
    private lateinit var binding : FragmentCardsBinding
    private lateinit var viewModel: CardsFragmentViewModel
    private lateinit var cardAdapter: CardsAdapter
    private  var isOrder: Boolean = false
    private val navController: NavController by lazy { findNavController() }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        hideAppbarHomeIcon(this, true, "Cards")
        binding = FragmentCardsBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[CardsFragmentViewModel::class.java]
        binding.emptyItemLayout.emptyOrderImage.setImageDrawable(requireContext().getDrawable(R.drawable.icons_shop_card))
        binding.emptyItemLayout.title.text = getString(R.string.str_your_cards)
        binding.emptyItemLayout.navigateButton.setOnClickListener {
            navController.navigate(R.id.homePageFragment)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.text = "$$it"
            getOrderPriceAndSetUI(it)
        }

        viewModel.products.observe(viewLifecycleOwner) { it ->
            if(it.isEmpty()) {
                binding.emptyItemLayout.mainLayout.visibility = View.VISIBLE
            }else {
                binding.emptyItemLayout.mainLayout.visibility = View.GONE
            }
            cardAdapter = CardsAdapter(it,
                onDeleteButton = {viewModel.removeProduct(it)},
                onstokProcess = {viewModel.getTotalPrice(it)}
            )
                binding.rcCard.adapter = cardAdapter
        }

        viewModel.isResponse.observe(viewLifecycleOwner) {
            if(it) {
               showShortToast(requireContext(), requireContext().getString(R.string.str_islem_basarili))
               isOrder = it
           }else {
               showShortToast(requireContext(), requireContext().getString(R.string.str_hata))
           }
        }
        binding.orderButton.setOnClickListener {
            val jwtUser = SharedManagment.getJwtModel()
            viewModel.postOrder(cardAdapter.getCardProductList(), jwtUser)
        }

        viewModel.size.observe(viewLifecycleOwner) {
            setBadge(it)
        }

        return binding.root
    }


    fun getOrderPriceAndSetUI(totalPrice: Double) {
        val delivery = binding.deliveryPrice.text.toString()
        val discount = binding.discountPrice.text.toString()
        val buttonText = requireContext().getText(R.string.str_alisverisi_tamamla)
        val total = viewModel.calculateOrderPrice(totalPrice, discount, delivery).toString()
        binding.orderButton.text = "$buttonText $$total"
    }

    private fun setBadge(productSize: Int){
        val mainActivity = requireActivity()
        if(mainActivity is MainActivity){
            mainActivity.setBadge(productSize)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideAppbarHomeIcon(this, false)
        if(isOrder) {
            //kullanıcı sepetten çıktıktan sonra sepetteki ürünleri siler.
            viewModel.clearAllCardItems()
            setBadge(0)
        }
    }
}