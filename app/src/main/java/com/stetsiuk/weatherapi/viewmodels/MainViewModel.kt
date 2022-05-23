package com.stetsiuk.weatherapi.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stetsiuk.weatherapi.data.GeocodingRepository
import com.stetsiuk.weatherapi.data.OnecallRepository
import com.stetsiuk.weatherapi.models.geocoding.Geocoding
import com.stetsiuk.weatherapi.models.onecall.OneCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val onecallRepository: OnecallRepository,
): ViewModel() {
    val searchResultGeocodings = MutableLiveData<List<Geocoding>>()
    val currentSelectedGeocoding = MutableLiveData<Geocoding>()
    val currentOneCall = MutableLiveData<OneCall>()
    val lastUserLocation = MutableLiveData<Location>()
    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable.localizedMessage.let { it.orEmpty() })
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    fun clearSearchResults(){
        searchResultGeocodings.value = emptyList()
    }

    fun refresh(){
        currentSelectedGeocoding.value?.let { getOneCallByCoordinates(it.lat, it.lon) }
    }

    fun getGeocodingsByName(name: String){
        viewModelScope.launch(exceptionHandler) {
            val response = geocodingRepository.getByName(name)
            if(response.isSuccessful){
                searchResultGeocodings.postValue(response.body())
            } else {
                onError(response.message())
            }
        }
    }

    fun getOneCallByCoordinates(lat: Double, lon: Double){
        viewModelScope.launch(exceptionHandler) {
            val response = onecallRepository.get(lat, lon)
            if(response.isSuccessful){
                currentOneCall.postValue(response.body())
            } else {
                onError(response.message())
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
        viewModelScope.launch(exceptionHandler) {
            val response = geocodingRepository.getByCoordinates(lat, lon)
            if(response.isSuccessful){
                currentSelectedGeocoding.postValue(response.body()?.first())
            } else {
                onError(response.message())
            }
        }
    }
}