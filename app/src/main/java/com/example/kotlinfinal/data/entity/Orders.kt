package com.example.kotlinfinal.data.entity

data class Order(
    val userId: Int,
    val products: List<OrdersItem>
)

data class OrdersItem(
    val id: Long,
    val quantity: Long)

data class AddtoCardResponse (
    val id: Long,
    val products: List<Product>,
    val total: Long,
    val discountedTotal: Long,
    val userID: Long,
    val totalProducts: Long,
    val totalQuantity: Long)



data class CartResponse(
    val carts: List<Cart>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Cart(
    val id: Long,
    val products: List<CardProduct>,
    val total: Int,
    val discountedTotal: Int,
    val userId: Long,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class CardProduct(
    val id: Long,
    val title: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
    val discountPercentage: Double,
    val discountedPrice: Int,
    val thumbnail: String
)