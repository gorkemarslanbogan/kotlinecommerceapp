package com.example.kotlinfinal.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.helper.SHARED_KEY
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseProductProcessViewModel
import kotlinx.coroutines.launch

class FavroiesFragmentViewModel : BaseProductProcessViewModel() {
    init {
        getProducts()
    }
    override fun getProducts() {
        viewModelScope.launch{
            val productList = SharedManagment.getProducts(SHARED_KEY.GET_MY_FAVORI_PRODUCT.name)
            productList.let { products ->
                _products.value = products
            }
        }
    }
}

