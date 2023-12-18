package com.example.kotlinfinal.data.repo

import com.example.kotlinfinal.data.entity.AddtoCardResponse
import com.example.kotlinfinal.data.entity.CartResponse
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.data.entity.Order
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.data.entity.ProductWithCategories
import com.example.kotlinfinal.data.entity.Products
import com.example.kotlinfinal.data.entity.UpdateUserResponse
import com.example.kotlinfinal.data.entity.UserLogin
import com.example.kotlinfinal.network.client.ApiClient
import com.example.kotlinfinal.network.service.DummyService

class DummyServiceRepository {
    private var dummyService: DummyService = ApiClient.getApiClient().create(DummyService::class.java)

    suspend fun login(userLogin: UserLogin) : JwtUser {
        return dummyService.login(userLogin)
    }

    suspend fun getProducts() : Products {
        return dummyService.getProducts()
    }

    suspend fun searchProduct(query: String) : Products {
        return dummyService.searchProduct(query)
    }

    suspend fun getProductById(productId: String) : Product {
        return dummyService.getProductById(productId)
    }

    suspend fun getProductCategories() : MutableList<String> {
        return dummyService.getProductCategories()
    }

    suspend fun getProductByCategories(categoriesName: String) : ProductWithCategories {
        return dummyService.getProductByCategories(categoriesName)
    }

    suspend fun addOrder(order: Order) : AddtoCardResponse {
        return dummyService.addOrder(order)
    }

    suspend fun getOrders(userid: Long) : CartResponse {
        return dummyService.getOrders(userid)
    }

    suspend fun updateUser(userid: Long, updateUserModel: JwtUser) : UpdateUserResponse? {
        return dummyService.updateUser(userid, updateUserModel)
    }
}