package com.example.kotlinfinal.network.service

import com.example.kotlinfinal.data.entity.AddtoCardResponse
import com.example.kotlinfinal.data.entity.CartResponse
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.data.entity.Order
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.data.entity.ProductWithCategories
import com.example.kotlinfinal.data.entity.Products
import com.example.kotlinfinal.data.entity.UpdateUserResponse
import com.example.kotlinfinal.data.entity.UserLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyService {

    @POST("auth/login")
    suspend fun login(@Body userLogin: UserLogin) : JwtUser

    @GET("products")
    suspend fun getProducts() : Products

    @GET("products/search?")
    suspend fun searchProduct(@Query("q") query :String) : Products

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId :String) : Product

    @GET("products/categories")
    suspend fun getProductCategories() : MutableList<String>

    @GET("products/category/{categoriesName}")
    suspend fun getProductByCategories(@Path("categoriesName") categoriesName: String) : ProductWithCategories

    @POST("carts/add")
    suspend fun addOrder(@Body order: Order) : AddtoCardResponse

    @GET("carts/user/{id}")
    suspend fun getOrders(@Path("id") userId: Long = 5): CartResponse


    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: Long, @Body updateUserModel: JwtUser) : UpdateUserResponse
}