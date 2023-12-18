package com.example.kotlinfinal.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.kotlinfinal.R
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.data.entity.Product
import com.example.kotlinfinal.helper.Constant.SHARED_LIST_ADD
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface ISharedManagment{

    fun addOrRemoveProduct(key:String, processType: Int, product: Product) : Boolean
    fun getProducts(key:String) : MutableList<Product>
    fun clearProduct() : MutableList<Product>
    fun saveProducts(key: String, productList: List<Product>)
    fun saveJwtModel(jwtUser: JwtUser)
    fun getJwtModel() : JwtUser

}

object SharedManagment : ISharedManagment {

    private var sharedPreferences: SharedPreferences? = null
    private var sharedEditor: SharedPreferences.Editor? = null
    private val gson: Gson by lazy { Gson() }


     fun getSharedInstance(context: Context) : SharedPreferences.Editor {
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getString(R.string.str_shared_name), Context.MODE_PRIVATE)
        }
        if(sharedEditor == null) {
            sharedEditor = sharedPreferences!!.edit()
        }
        return sharedEditor!!
    }

    override fun addOrRemoveProduct(key: String, processType: Int, product: Product) : Boolean {
        val myProductList = getProducts(key)
        return try {
            if(processType == SHARED_LIST_ADD)
                myProductList.add(product) else
                    myProductList.remove(product)
            saveProducts(key, myProductList)
            true
        }catch (e: Exception){
            false
        }
    }

    override fun getProducts(key: String): MutableList<Product> {
        val myproductList = sharedPreferences?.getString(key, "")
        if(!myproductList.isNullOrEmpty()){
            val type = object : TypeToken<MutableList<Product>>() {}.type
            return gson.fromJson(myproductList, type)
        }
        return mutableListOf()
    }

    override fun clearProduct(): MutableList<Product> {
        sharedEditor?.remove(SHARED_KEY.GET_MY_CARD_PRODUCT.name)?.apply()
        return getProducts(SHARED_KEY.GET_MY_CARD_PRODUCT.name)
    }

    override fun saveProducts(key: String, productList: List<Product>) {
        val jsonModel = gson.toJson(productList)
        sharedEditor?.putString(key, jsonModel)?.apply()
    }

    override fun saveJwtModel(jwtUser: JwtUser) {
        val jsonModel = gson.toJson(jwtUser)
        sharedEditor?.putString(SHARED_KEY.SAVE_JWT_USER.name, jsonModel)?.apply()
    }

    override fun getJwtModel(): JwtUser {
        val jsonModel = sharedPreferences?.getString(SHARED_KEY.SAVE_JWT_USER.name, "")
        return gson.fromJson(jsonModel, JwtUser::class.java)
    }
}

enum class SHARED_KEY {
    GET_MY_FAVORI_PRODUCT,
    GET_MY_CARD_PRODUCT,
    SAVE_JWT_USER
}