package com.stetsiuk.weatherapi

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stetsiuk.weatherapi.repository.GeocodingRepository
import com.stetsiuk.weatherapi.repository.OnecallRepository
import com.stetsiuk.weatherapi.repository.net.geocoding.model.Geocoding
import com.stetsiuk.weatherapi.repository.net.onecall.Units
import com.stetsiuk.weatherapi.repository.net.onecall.model.OneCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val onecallRepository: OnecallRepository): ViewModel() {

    val searchResultGeocodings = MutableLiveData<List<Geocoding>>()
    val currentSelectedGeocoding = MutableLiveData<Geocoding>()
    val currentOneCall = MutableLiveData<OneCall>()
    val lastUserLocation = MutableLiveData<Location>()
    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Error: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    fun clearSearchResults(){
        searchResultGeocodings.value = emptyList()
    }

    fun refresh(){
        currentSelectedGeocoding.value?.let { getOneCallByCoordinates(currentSelectedGeocoding.value!!.lat, it.lon) }
    }

    fun getGeocodingsByName(name: String){
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            val response = geocodingRepository.getByName(name)
            if(response.isSuccessful){
                searchResultGeocodings.postValue(response.body())
            } else {
                onError("Response is not success!")
            }
        }
    }

    fun getOneCallByCoordinates(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            //units
            val response = onecallRepository.get(lat, lon, units = Units.metric)
            if(response.isSuccessful){
                currentOneCall.postValue(response.body())
            } else {
                onError("Response is not success!")
            }
        }
    }

    fun setLastUserLocation(location: Location){
        lastUserLocation.value = location
    }

    fun setCurrentGeocoding(geocoding: Geocoding){
        currentSelectedGeocoding.value = geocoding
    }

    fun setCurrentGeocodingWithLatLon(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            val response = geocodingRepository.getByCoordinates(lat, lon)
            if(response.isSuccessful){
                currentSelectedGeocoding.postValue(response.body()?.let { it[0] })
            } else {
                onError("Response is not success!")
            }
        }
    }
}