package com.example.kotlinfinal.data.entity

data class ProductWithCategories (
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long
)

