package com.gaurav.weatherapp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gaurav.gojekassignment.data.model.ForecastDayDto
import com.gaurav.gojekassignment.util.Utils
import com.gaurav.weatherapp.R

open class DaysTemperatureAdapter(private var mForecastDay: List<ForecastDayDto>, private val context: Context) :
    RecyclerView.Adapter<DaysTemperatureAdapter.TemperatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.days_recyclerview_row, parent, false)

        return TemperatureViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TemperatureViewHolder, position: Int) {
        val forecastDayDto = getItemAtPosition(position)

        holder.tvDay.text = Utils(context).getDay(forecastDayDto.date)
        holder.tvDayTemperature.text = "" + forecastDayDto.day.avgtemp_c + " " +context.getString(R.string.celsius);

    }

    fun getItemAtPosition(position: Int) : ForecastDayDto {
        return mForecastDay[position]
    }

    fun setDaysTemperatures(forecastDay: List<ForecastDayDto>) {
        this.mForecastDay = forecastDay
    }

    override fun getItemCount(): Int {
        return mForecastDay.size
    }

    class TemperatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay = itemView.findViewById(R.id.tvDay) as TextView
        var tvDayTemperature = itemView.findViewById(R.id.tvDayTemperature) as TextView

    }
}