package com.example.kotlinfinal.ui.viewmodel

import com.example.kotlinfinal.ui.viewmodel.baseviewmodel.BaseProductProcessViewModel

class CategoriesFragmentViewModel : BaseProductProcessViewModel(){
    init {
        getCategories()
    }
}