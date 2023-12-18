package com.example.kotlinfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.Cart
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseProductProcessViewModel
import kotlinx.coroutines.launch

class OrdersFragmentViewModel : BaseProductProcessViewModel() {
    private val _orders = MutableLiveData<List<Cart>>()
    val orders: LiveData<List<Cart>> get() = _orders
    init {
        getOrderList()
    }

      private fun getOrderList() {
        viewModelScope.launch{
            val orderList = repository.getOrders(5)
            orderList.let {
                _orders.value = orderList.carts
            }
        }
    }
}

