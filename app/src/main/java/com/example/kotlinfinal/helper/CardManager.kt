package com.example.kotlinfinal.helper

import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.helper.Constant.SHARED_LIST_ADD
import com.example.kotlinfinal.helper.Constant.SHARED_LIST_REMOVE


interface ICardManager {
    fun getProducts() : MutableList<Product>
    fun addProduct(product: Product) : MutableList<Product>
    fun removeProduct(product: Product) : MutableList<Product>
    fun clearProducts() : MutableList<Product>
    fun saveAllProduct(productList: List<Product>)
    fun getTotalPrice(productList: List<Product>) : Double
}

class CardManager: ICardManager {
    private val sharedManager = SharedManagment
    private lateinit var productList : MutableList<Product>

    init {
        getProducts()
    }
    override fun getProducts(): MutableList<Product> {
        productList = sharedManager.getProducts(SHARED_KEY.GET_MY_CARD_PRODUCT.name)
        return productList
    }

    override fun addProduct(product: Product): MutableList<Product> {
       val oldProduct = productList.find { it.id == product.id }
        if(oldProduct != null) {
            oldProduct.stock += product.stock
            saveAllProduct(productList)
        }else {
            sharedManager.addOrRemoveProduct(SHARED_KEY.GET_MY_CARD_PRODUCT.name, SHARED_LIST_ADD, product)
            productList.add(product)
        }
        return productList
    }

    override fun removeProduct(product: Product): MutableList<Product> {
        val isSucces = sharedManager.addOrRemoveProduct(SHARED_KEY.GET_MY_CARD_PRODUCT.name, SHARED_LIST_REMOVE, product)
        if(isSucces) {
            productList.remove(product)
        }
        return productList
    }

    override fun clearProducts(): MutableList<Product> {
        return sharedManager.clearProduct()
    }

    override fun saveAllProduct(productList: List<Product>) {
        sharedManager.saveProducts(SHARED_KEY.GET_MY_CARD_PRODUCT.name, productList)
    }

    override fun getTotalPrice(productList: List<Product>): Double {
        var totalPrice = 0.0
        productList.forEach {
            totalPrice += it.stock * it.price
        }
        return totalPrice
    }


}