package com.example.kotlinfinal.ui.viewmodel.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.helper.CardManager

abstract class BaseCardViewModel : BaseProductProcessViewModel() {
    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int> get() = _size
    val cardManager: CardManager by lazy { CardManager() }

    open fun getAllCardProduct(){}

    open fun removeProduct(product: Product){}

    open fun getCardItemSize() {
        _size.value = cardManager.getProducts().size
    }
}