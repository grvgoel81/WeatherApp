package com.gaurav.weatherapp.ui.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.gaurav.gojekassignment.data.model.DaysTemperatureDTO
import com.gaurav.gojekassignment.util.CheckInternetConnection
import com.gaurav.gojekassignment.util.GeoCodeUtils
import com.gaurav.gojekassignment.viewmodel.MainActivityViewModel
import com.gaurav.gojekassignment.viewmodel.MainViewModel
import com.gaurav.weatherapp.R
import com.gaurav.weatherapp.ui.adapter.DaysTemperatureAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val REQUEST_LOCATION = 1
    private lateinit var locationManager: LocationManager
    lateinit var latitude: String
    lateinit var longitude: String
    private var behavior: BottomSheetBehavior<*>? = null
    private var mBottomSheetDialog: BottomSheetDialog? = null
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            noGpsDialog()

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation()
        }

        behavior = BottomSheetBehavior.from<View>(bottom_sheet)
        (behavior as BottomSheetBehavior<View>?)?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

        btnRetry.setOnClickListener {
            ll_error.visibility = View.GONE
            getLocation()
        }

    }

    private fun noGpsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.gps_connection_turn_on))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton(getString(R.string.no)) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)

        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val passiveLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            when {
                location != null -> {
                    val lat = location.latitude
                    val long = location.longitude
                    latitude = lat.toString()
                    longitude = long.toString()

                }
                gpsLocation != null -> {
                    val lat = gpsLocation.latitude
                    val long = gpsLocation.longitude
                    latitude = lat.toString()
                    longitude = long.toString()

                }
                passiveLocation != null -> {
                    val lat = passiveLocation.latitude
                    val long = passiveLocation.longitude
                    latitude = lat.toString()
                    longitude = long.toString()

                }
                else -> Toast.makeText(this, getString(R.string.trace_location), Toast.LENGTH_SHORT).show()
            }
        }

        var city = ""
        try {
            city = GeoCodeUtils(applicationContext).getCity(latitude, longitude)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val factory = MainViewModel(city, applicationContext)
        mainActivityViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        getWeatherData()
    }

    private fun getWeatherData() {
        if (CheckInternetConnection.isNetworkAvailable(this@MainActivity)) {
            progressBar.visibility = View.VISIBLE
            mainActivityViewModel.mLiveData()?.observe(this,
                Observer<DaysTemperatureDTO> { temperatureResults ->
                    progressBar.visibility = View.GONE
                    if (temperatureResults != null) {
                        ll_error.visibility = View.GONE
                        tvCity.text = temperatureResults.location.name
                        tvTemperature.text = "" + temperatureResults.current.temp_c
                        showBottomSheetDialog(temperatureResults)
                    } else {
                        ll_error.visibility = View.VISIBLE
                    }
                })
        } else {
            progressBar.visibility= View.GONE
            ll_error.visibility = View.VISIBLE
        }
    }

    /*
     * For showing temperature of different days
     * */
    private fun showBottomSheetDialog(daysTemperatureDTO: DaysTemperatureDTO) {
        if (behavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        mBottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.temperature_bottomsheet_layout, null)
        val temperatureRecyclerView = view.findViewById<View>(R.id.temperatureRecyclerView) as RecyclerView
        val mLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        temperatureRecyclerView.layoutManager = mLayoutManager
        temperatureRecyclerView.itemAnimator = DefaultItemAnimator()
        temperatureRecyclerView.isNestedScrollingEnabled = false

        val temperatureAdapter = DaysTemperatureAdapter(daysTemperatureDTO.forecast.forecastday, this@MainActivity)
        temperatureRecyclerView.adapter = temperatureAdapter

        mBottomSheetDialog!!.setContentView(view)
        mBottomSheetDialog!!.show()
        mBottomSheetDialog!!.setOnDismissListener(DialogInterface.OnDismissListener{ mBottomSheetDialog = null })
    }

}