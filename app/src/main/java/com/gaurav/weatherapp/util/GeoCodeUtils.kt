package com.gaurav.gojekassignment.util

import android.content.Context
import android.location.Geocoder
import java.io.IOException
import java.util.*

class GeoCodeUtils(context: Context) {

    private val geoCoder : Geocoder = Geocoder(context, Locale.getDefault())

    @Throws(IOException::class)
    fun getCity(latitude: String, longitude: String): String {
        val addressList = geoCoder.getFromLocation(java.lang.Double.valueOf(latitude), java.lang.Double.valueOf(longitude), 1)
        return addressList[0].locality
    }

}