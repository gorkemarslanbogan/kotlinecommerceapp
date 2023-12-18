package com.example.kotlinfinal.ui.viewmodel.baseviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfinal.data.entity.JwtUser
import com.example.kotlinfinal.data.repo.DummyServiceRepository

abstract class BaseViewModel : ViewModel() {
     val repository: DummyServiceRepository by lazy { DummyServiceRepository() }
     val jwtUserLiveData = MutableLiveData<JwtUser?>()
     var jwtUser: JwtUser?
          get() = jwtUserLiveData.value
          set(value) { jwtUserLiveData.value = value }
}