package com.stetsiuk.weatherapi.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stetsiuk.weatherapi.R
import com.stetsiuk.weatherapi.databinding.DailyWeatherItemBinding
import com.stetsiuk.weatherapi.repository.net.MainData.iconExtention
import com.stetsiuk.weatherapi.repository.net.MainData.iconsUrl
import com.stetsiuk.weatherapi.models.onecall.daily.Daily
import java.text.SimpleDateFormat
import java.util.*

class DailyForecastAdapter: ListAdapter<Daily, DailyForecastAdapter.DailyForecastViewHolder>(
    diffCalback
) {

    object diffCalback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily) =
            oldItem==newItem
        override fun areContentsTheSame(oldItem: Daily, newItem: Daily) =
            oldItem==newItem
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DailyWeatherItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.daily_weather_item, parent, false)
        return DailyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val item: Daily = getItem(position)
        holder.bind(item)
    }

    class DailyForecastViewHolder(
        val binding: DailyWeatherItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

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
            binding.dt = getFormattedDate(daily.dt, binding.root.context.resources.getString(R.string.main_date_pattern), Locale.ENGLISH)
            binding.sunrise = getFormattedDate(daily.sunrise, binding.root.context.resources.getString(R.string.time_pattern))
            binding.sunset = getFormattedDate(daily.sunset, binding.root.context.resources.getString(R.string.time_pattern))
            binding.moonrise = getFormattedDate(daily.moonrise, binding.root.context.resources.getString(R.string.time_pattern))
            binding.moonset = getFormattedDate(daily.moonset, binding.root.context.resources.getString(R.string.time_pattern))

            Picasso.get()
                .load("${iconsUrl}${daily.weather.first().icon}${iconExtention}")
                .resize(binding.root.context.resources.getDimension(R.dimen.loaded_icon_width).toInt(), binding.root.context.resources.getDimension(R.dimen.loaded_icon_height).toInt())
                .centerCrop()
                .into(binding.iwWeatherIcon);

        }

        private fun getFormattedDate(
            unixDate: Long,
            formatPattern: String,
            locale: Locale = Locale.getDefault()
        ) = SimpleDateFormat(formatPattern, locale).format(Date(unixDate*1000))
    }
}