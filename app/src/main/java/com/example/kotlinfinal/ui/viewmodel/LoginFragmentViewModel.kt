package com.example.kotlinfinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinfinal.data.entity.UserLogin
import com.example.kotlinfinal.data.entity.UserResponse
import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseViewModel
import kotlinx.coroutines.launch

class LoginFragmentViewModel : BaseViewModel() {
    suspend fun checkLogin(username: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            val userLogin = UserLogin(username, password)
            try {
                jwtUser = repository.login(userLogin)
                if(jwtUser != null){
                    result = true
                }
            }catch (e: Exception) {
                e.printStackTrace()
                result = false
            }
        }.join()
        return result
    }
}