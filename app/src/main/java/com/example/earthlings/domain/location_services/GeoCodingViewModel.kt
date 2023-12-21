/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 2
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/12/2023
    Last modified: 20/12/2023
    Acknowledgement: Figma UI, Android Developer documentation, Firebase Documentation, etc
 */


package com.example.earthlings.domain.location_services

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.earthlings.model.EventData
import com.example.earthlings.model.MarkerInfo
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale


/**
 * Handle Geolocation events
 */
class GeocodingViewModel(application: Application) : AndroidViewModel(application) {
    private val _eventToMarkerMap = MutableLiveData<Map<String, MarkerInfo>>()
    val eventToMarkerMap: LiveData<Map<String, MarkerInfo>> = _eventToMarkerMap

    fun geocodeEvents(events: List<EventData>) {
        val map = hashMapOf<String, MarkerInfo>()
        events.forEach { eventData ->
            geocodeAddress(eventData.location) { latLng ->
                Log.i("data","$latLng")
                eventData.formID?.let { map.put(it, MarkerInfo(latLng, eventData.imageUrl)) }
                _eventToMarkerMap.postValue(map)
            }
        }
    }

    private fun geocodeAddress(address: String, callback: (LatLng) -> Unit) {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(address, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val location = addresses?.get(0)
                    if (location != null) {
                        callback(LatLng(location.latitude, location.longitude))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback(LatLng(0.0, 0.0))
        }
    }
}


/**
 * Function to geocode an address string

 */
fun geocodeAddress(context: Context, addressString: String): LatLng? {
    val geocoder = Geocoder(context)
    return try {
        val addressList = geocoder.getFromLocationName(addressString, 1)
        if (addressList?.isNotEmpty() == true) {
            val address = addressList[0]
            LatLng(address.latitude, address.longitude)
        } else {
            null
        }
    } catch (e: IOException) {
        // Handle the exception (e.g., network issue)
        null
    }
}