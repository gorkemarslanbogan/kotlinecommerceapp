package com.example.kotlinfinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.data.entity.Order
import com.example.kotlinfinal.data.entity.OrdersItem
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseCardViewModel
import kotlinx.coroutines.launch

class CardsFragmentViewModel : BaseCardViewModel() {
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> get() = _totalPrice
    private val _isResponse = MutableLiveData<Boolean>()
    val isResponse: LiveData<Boolean> get() = _isResponse
    init {
        getAllCardProduct()
    }

    override fun getAllCardProduct(){
        val productList = cardManager.getProducts()
        _products.postValue(productList)
        getTotalPrice(productList)
    }

    override fun removeProduct(product: Product){
        _products.postValue(cardManager.removeProduct(product))
        getCardItemSize()
    }


    fun getTotalPrice(product: List<Product>){
        _totalPrice.postValue(cardManager.getTotalPrice(product))
    }

    fun calculateOrderPrice(totalPrice: Double, discount: String, deliveryPrice: String) : Double {
        val deliveryPrice = deliveryPrice.replace("$", "")
        val discount = discount.replace("%", "")
        var orderPrice = totalPrice
        return try {
            val totalDiscount = (totalPrice * discount.trim().toInt() / 100)
            orderPrice += deliveryPrice.trim().toDouble()
            orderPrice -= totalDiscount
            orderPrice
        }catch (e: Exception) {
            e.printStackTrace()
            orderPrice
        }
    }

    fun postOrder(product: List<Product>, jwtUser: JwtUser)  {
        val orderItemList = mutableListOf<OrdersItem>()
        product.forEach {
            val orderItem = OrdersItem(it.id, it.stock)
            orderItemList.add(orderItem)
        }
        val order = jwtUser.id?.let {
            Order(it.toInt(), orderItemList)

        }
        viewModelScope.launch {
            val response = order?.let { repository.addOrder(it) }
            response.let {
                _isResponse.postValue(it!!.products.isNotEmpty())
            }

        }
    }

    fun clearAllCardItems() {
        _products.postValue(cardManager.clearProducts())
    }
}