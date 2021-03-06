package com.stetsiuk.weatherapi

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.stetsiuk.weatherapi.adapters.DailyForecastAdapter
import com.stetsiuk.weatherapi.models.geocoding.Geocoding
import com.stetsiuk.weatherapi.adapters.SearchResultsAdapter
import com.stetsiuk.weatherapi.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @Inject lateinit var location: FusedLocationProviderClient

    private lateinit var searchView: SearchView
    private lateinit var rwSearchRes: RecyclerView
    private lateinit var rwForecast: RecyclerView
    private lateinit var btnCurrentLocation: Button
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation)
        btnRefresh = findViewById(R.id.btnRefresh)
        rwSearchRes = findViewById(R.id.rwSearchResult)
        rwForecast = findViewById(R.id.rwForecast)
        searchView = findViewById(R.id.searchViewNames)

        btnCurrentLocation.setOnClickListener{
            requestCurrentLocation(true)
        }

        btnRefresh.setOnClickListener{
            refreshLocation()
            mainViewModel.refresh()
            searchView.setQuery(String(), false)
            searchView.clearFocus()
        }

        val searchAdapter = SearchResultsAdapter(object: SearchResultsAdapter.OnItemClickListener{
            override fun onItemClick(geocoding: Geocoding) {
                mainViewModel.setCurrentGeocoding(geocoding)
            }
        })
        rwSearchRes.adapter = searchAdapter

        val forecastAdapter = DailyForecastAdapter()
        rwForecast.adapter = forecastAdapter

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let{
                    if(p0.isEmpty()) mainViewModel.clearSearchResults()
                    else mainViewModel.getGeocodingsByName(p0)
                }
                return false
            }

        })

        mainViewModel.currentSelectedGeocoding.observe(this, {
            searchView.setQuery(String(), false)
            searchView.clearFocus()
            mainViewModel.getOneCallByCoordinates(it.lat, it.lon)
            searchView.queryHint = it.name
        })

        mainViewModel.currentOneCall.observe(this, {
            forecastAdapter.submitList(it.daily)
        })

        mainViewModel.searchResultGeocodings.observe(this, {
            searchAdapter.submitList(it)
        })

        mainViewModel.errorMessage.observe(this, {
            Log.e("weatherE", it)
            Toast.makeText(this, resources.getString(R.string.ui_err_msg), Toast.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        refreshLocation()
    }

    private fun refreshLocation(){
        if(mainViewModel.currentSelectedGeocoding.value==null){
            requestCurrentLocation(true)
        } else {
            requestCurrentLocation()
        }
    }

    private fun requestCurrentLocation(setAsCurrent: Boolean = false) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_REQUEST_CODE
            )
        }
        location.lastLocation.addOnSuccessListener {
            it?.let {
                mainViewModel.setLastUserLocation(it)
                if(setAsCurrent) mainViewModel.setCurrentGeocodingWithLatLon(it.latitude, it.longitude)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== LOCATION_REQUEST_CODE) {
            if (!grantResults.isEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("weatherD", "Permission granted")
            } else {
                Log.d("weatherD", "Permission not granted")
            }
        }
    }

    companion object{
        const val LOCATION_REQUEST_CODE = 0
    }
}