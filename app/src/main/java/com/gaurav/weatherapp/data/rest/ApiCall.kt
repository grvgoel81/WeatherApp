package com.gaurav.gojekassignment.data.rest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.gaurav.gojekassignment.data.model.DaysTemperatureDTO
import com.gaurav.gojekassignment.util.APIConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

object ApiCall {

    private val data = MutableLiveData<DaysTemperatureDTO>()

    private var mWeatherObservable: Observable<DaysTemperatureDTO>? = null

    private val compositeDisposable = CompositeDisposable()

    val weatherData: LiveData<DaysTemperatureDTO>
        get() = data

    fun getWeatherData(city: String, days: Int) {
        val apiService = ApiClient.client?.create(ApiInterface::class.java)
        mWeatherObservable = apiService?.getForecast(APIConstants.API_KEY, city, days)
        compositeDisposable.add(mWeatherObservable!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<DaysTemperatureDTO>() {
                override fun onNext(daysTemperatureDTO: DaysTemperatureDTO) {
                    data.postValue(daysTemperatureDTO)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
        )
    }

}