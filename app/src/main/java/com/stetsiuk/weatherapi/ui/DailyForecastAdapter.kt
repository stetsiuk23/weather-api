package com.stetsiuk.weatherapi.ui

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stetsiuk.weatherapi.R
import com.stetsiuk.weatherapi.databinding.DailyWeatherItemBinding
import com.stetsiuk.weatherapi.repository.net.IconsUrl
import com.stetsiuk.weatherapi.repository.net.onecall.model.daily.Daily
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import androidx.databinding.BindingAdapter

class DailyForecastAdapter (): ListAdapter<Daily, DailyForecastAdapter.DailyForecastViewHolder>(diffCalback) {

    object diffCalback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily) = oldItem==newItem
        override fun areContentsTheSame(oldItem: Daily, newItem: Daily) = oldItem==newItem
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val binding: DailyWeatherItemBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.daily_weather_item, parent, false)
        return DailyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val item: Daily = getItem(position)
        holder.bind(item)
    }


    class DailyForecastViewHolder(val binding: DailyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (binding.dailyForecastlongInfo.visibility == View.GONE)
                    binding.dailyForecastlongInfo.visibility = View.VISIBLE
                else binding.dailyForecastlongInfo.visibility = View.GONE
            }
        }

        fun bind(daily: Daily){
            binding.dailyForecastlongInfo.visibility = View.GONE

            binding.daily = daily
            binding.dt = SimpleDateFormat("EEE, d MMM", Locale.ENGLISH).format(Date(daily.dt*1000))
            binding.sunrise = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(daily.sunrise*1000))
            binding.sunset = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(daily.sunset*1000))
            binding.moonrise = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(daily.moonrise*1000))
            binding.moonset = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(daily.moonset*1000))

            Picasso.get().load("${IconsUrl}${daily.weather[0].icon}.png")
                .resize(200, 200).centerCrop()
                .into(binding.iwWeatherIcon);

        }
    }
}