package com.example.kotlinfinal.ui.viewmodel.baseviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.Product
import kotlinx.coroutines.launch


//ürün işlemleriyle ilgili viewmodel'ları ortaklaştırıp kod tekrarını azaltmak için üretildi.
//Görkem Arslanboğan 09.12.2023
abstract class BaseProductProcessViewModel : BaseViewModel() {
    open val _products = MutableLiveData<List<Product>>()
    open val products: LiveData<List<Product>> get() = _products
    private val _categories = MutableLiveData<MutableList<String>>()
    val categories: LiveData<MutableList<String>> get() = _categories

    open fun getProducts() {
        viewModelScope.launch{
            val productList = repository.getProducts()
            productList.let { products ->
                _products.value = products.products
            }
        }
    }

    open fun getCategories() {
        viewModelScope.launch{
             val productCategories = repository.getProductCategories()
             productCategories.let {
                 it.add(0, "All")
                _categories.postValue(productCategories)
            }
        }
    }

    open fun getProductByCategories(categoriesName: String) {
        viewModelScope.launch{
            val products = repository.getProductByCategories(categoriesName)
            products.let {
                _products.value = products.products
            }
        }
    }

    open fun getProductById(productId: String) {}

   open fun getProductWithCategoriesName(categoriesName: String){
        if(categoriesName == "All"){
            getProducts()
        } else {
            getProductByCategories(categoriesName)
        }
    }
}