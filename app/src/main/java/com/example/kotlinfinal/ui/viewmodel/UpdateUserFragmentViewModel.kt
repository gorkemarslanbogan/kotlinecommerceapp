package com.example.kotlinfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.helper.capitalizeAndReplace
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gorkemarslanbogan.ecommer1234.model.EditProfileModel
import kotlinx.coroutines.launch

class UpdateUserFragmentViewModel : BaseViewModel() {
    private var _isSuccesful = MutableLiveData<Boolean>()
    val isSuccesful: LiveData<Boolean> get() = _isSuccesful
    private var _edittextProfileModel = MutableLiveData<List<EditProfileModel>>()
    val edittextProfileModel: LiveData<List<EditProfileModel>> get() = _edittextProfileModel



    fun setEdittextProfileList(jwtUser: JwtUser){

        // TODO:  GORKEM ARSLANBOGAN 15.12.2023
        //burada modeli map'e dönüştürüp key değerleriyle güncellenecek değerleri alıp göstermek
        //böylece modele yeni bir data eklenirse burası otomatik olarak ekleyecek ve
        //güncelleme sayfamız dinamik olacaktır.
        val gson = Gson()
        val editTextProfileList = mutableListOf<EditProfileModel>()
        val json = gson.toJson(jwtUser)
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        val map: Map<String, Any> = gson.fromJson(json, mapType)
        map.forEach { (k, v) ->
            if(k != "id" && k != "token" && k != "image"){
                editTextProfileList.add(EditProfileModel(k.capitalizeAndReplace(), v.toString(), true))
            }
        }
        _edittextProfileModel.postValue(editTextProfileList)
    }

     fun setUpdateModel(values: List<EditProfileModel>, jwtUser: JwtUser) {
        val newJwtUser = jwtUser
        values.forEach { editProfileModel ->
            //değişen ilgili yerlerin adlarını kontrol ediyorum hangisi değişmiş ise onları updatedJwtUser'a ekleyip servise güncellemek için isteği gönderiyorum.
            when (editProfileModel.optionsName) {
                "Firstname" -> {
                    if(jwtUser.firstName?.lowercase() != editProfileModel.optionsNameValue.lowercase()){
                        newJwtUser.firstName = editProfileModel.optionsNameValue
                    }
                }
                "Lastname" -> {
                    if(jwtUser.lastName?.lowercase() != editProfileModel.optionsNameValue.lowercase()){
                        newJwtUser.lastName = editProfileModel.optionsNameValue
                        newJwtUser.lastName = editProfileModel.optionsNameValue
                    }
                }
                "Email" -> {
                    if(jwtUser.email?.lowercase() != editProfileModel.optionsNameValue.lowercase()){
                        newJwtUser.email = editProfileModel.optionsNameValue
                    }
                }
                "Username" -> {
                    if(jwtUser.username?.lowercase() != editProfileModel.optionsNameValue.lowercase()){
                        newJwtUser.username = editProfileModel.optionsNameValue
                    }
                }
                "Gender" -> {
                    if(jwtUser.gender?.lowercase() != editProfileModel.optionsNameValue.lowercase()){
                        newJwtUser.gender = editProfileModel.optionsNameValue
                    }
                }
            }
        }
         jwtUser.id.let {
             viewModelScope.launch {
                val updateResponse =  repository.updateUser(it!!, newJwtUser)
                 _isSuccesful.postValue(updateResponse != null)
             }
         }
    }
}