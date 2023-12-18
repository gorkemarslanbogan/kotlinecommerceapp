package com.example.kotlinfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.data.repo.DummyServiceRepository
import kotlinx.coroutines.launch

class SearchProductFragmentViewModel : ViewModel() {
    private val repository: DummyServiceRepository by lazy { DummyServiceRepository() }
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun searchProduct(query: String) {
        if(query.isNotEmpty()){
            viewModelScope.launch{
                val productList = repository.searchProduct(query)
                productList.let { products ->
                    _products.value = products.products
                }
            }
        }else {
            _products.value = listOf()
        }
    }
}