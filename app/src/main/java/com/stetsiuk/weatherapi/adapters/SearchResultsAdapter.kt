package com.stetsiuk.weatherapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stetsiuk.weatherapi.models.geocoding.Geocoding

class SearchResultsAdapter(
    private val onItemClickListener: OnItemClickListener,
): ListAdapter<Geocoding, SearchResultsAdapter.CityItemViewHolder>(diffCalback){

    object diffCalback: DiffUtil.ItemCallback<Geocoding>() {
        override fun areItemsTheSame(oldItem: Geocoding, newItem: Geocoding) =
            oldItem==newItem
        override fun areContentsTheSame(oldItem: Geocoding, newItem: Geocoding) =
            oldItem==newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return CityItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val item: Geocoding = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }
        val result = "${item.name}${item.state?.let { ", $it" }}, ${item.country}"
        holder.bind(result)
    }

    class CityItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(geocoding_text: String){
            tvCityName.text = geocoding_text
        }
    }

    interface OnItemClickListener{
        fun onItemClick(geocoding: Geocoding)
    }
}