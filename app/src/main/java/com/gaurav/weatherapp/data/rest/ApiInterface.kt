package com.gaurav.gojekassignment.data.rest

import com.gaurav.gojekassignment.data.model.DaysTemperatureDTO
import com.gaurav.gojekassignment.util.APIConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(APIConstants.FORECAST)
    fun getForecast(@Query("key") key: String, @Query("q") city: String, @Query("days") days: Int): Observable<DaysTemperatureDTO>

}