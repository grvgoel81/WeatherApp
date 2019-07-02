package com.gaurav.gojekassignment.data.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.gaurav.gojekassignment.data.model.DaysTemperatureDTO
import com.gaurav.gojekassignment.data.rest.ApiCall

class Respository {

    private var mData: LiveData<DaysTemperatureDTO>? = null
    private var city: String

    constructor(city: String, context: Context) {
        this.city = city
        ApiCall.getWeatherData(city,4)
    }

    fun mLiveData(): LiveData<DaysTemperatureDTO>? {
        mData = ApiCall.weatherData
        return mData
    }

}
