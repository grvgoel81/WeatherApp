package com.gaurav.gojekassignment.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.gaurav.gojekassignment.data.model.DaysTemperatureDTO
import com.gaurav.gojekassignment.data.repository.Respository

class MainActivityViewModel(city: String, context: Context) : ViewModel() {

    private var mData: LiveData<DaysTemperatureDTO>? = null
    private val mRespository: Respository =
            Respository(city, context)

    fun mLiveData(): LiveData<DaysTemperatureDTO>? {
        mData = mRespository.mLiveData()
        return mData
    }
}
