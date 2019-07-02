package com.gaurav.gojekassignment.data.model

import java.io.Serializable

data class LocationDto(var name: String, var region: String, val country: String) : Serializable

data class DayDto(var avgtemp_c: Double) : Serializable

data class CurrentDto(var temp_f: Double, var temp_c: Double) : Serializable

data class ForecastDto(var forecastday: List<ForecastDayDto>) : Serializable

data class ForecastDayDto(var date: String, var day: DayDto) : Serializable

data class DaysTemperatureDTO(val location: LocationDto, val current: CurrentDto, val forecast: ForecastDto) : Serializable
