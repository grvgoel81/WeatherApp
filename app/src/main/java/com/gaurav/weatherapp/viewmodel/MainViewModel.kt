package com.gaurav.gojekassignment.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

class MainViewModel(private val city: String, private var context: Context) :
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(city, context) as T
    }

}