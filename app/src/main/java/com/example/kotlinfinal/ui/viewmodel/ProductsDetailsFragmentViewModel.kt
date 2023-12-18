package com.example.kotlinfinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.helper.Constant.SHARED_LIST_ADD
import com.example.kotlinfinal.helper.Constant.SHARED_LIST_REMOVE
import com.example.kotlinfinal.helper.SHARED_KEY
import com.example.kotlinfinal.helper.SharedManagment
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseCardViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProductsDetailsFragmentViewModel : BaseCardViewModel() {
    private var _productLiveData = MutableLiveData<Product>()
    val singleProduct: LiveData<Product> get() = _productLiveData
    private var _productIsLike = MutableLiveData<Boolean>()
    val productIsLike: LiveData<Boolean> get() = _productIsLike
    private var _productDisLike = MutableLiveData<Boolean>()
    val productDisLike: LiveData<Boolean> get() = _productDisLike


    private fun checkProductIsFavorite(productId: String){
        viewModelScope.launch {
            val products = SharedManagment.getProducts(SHARED_KEY.GET_MY_FAVORI_PRODUCT.name)
            for (product in products) {
                if (product.id.toString() == productId) {
                    _productIsLike.postValue(true)
                    break
                } else {
                    _productIsLike.postValue(false)
                }
            }
        }
    }

    fun addFavorite(product: Product) {
          val result = SharedManagment.addOrRemoveProduct(
            SHARED_KEY.GET_MY_FAVORI_PRODUCT.name,
            SHARED_LIST_ADD,
            product)
        _productIsLike.postValue(result)
    }

    fun removeFavorite(product: Product) {
        val result = SharedManagment.addOrRemoveProduct(
            SHARED_KEY.GET_MY_FAVORI_PRODUCT.name,
            SHARED_LIST_REMOVE,
            product)
        //ürün kaldırıldıysa false dönüp ui'da gerekli butonları açıp kapatıcak
        //false dönerse like butonu true dönerse unlike butonu açılacak.
        if(result) {
            _productDisLike.postValue(false)
        }

    }

    fun addToCard(product: Product) {
         product.stock = 1
         cardManager.addProduct(product)
         getCardItemSize()
    }

    override fun getProductById(productId: String) {
        checkProductIsFavorite(productId)
        viewModelScope.launch{
            val product = repository.getProductById(productId)
            product.let {
                _productLiveData.postValue(product)
            }
        }
    }

    fun randomInRange(start: Int, end: Int): Int {
        require(start <= end) {}
        return Random.nextInt(start, end + 1)
    }
}