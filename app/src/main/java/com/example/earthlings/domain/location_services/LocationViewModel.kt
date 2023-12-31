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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MainActivityViewModel:ViewModel() {

    private var _isNewLocationSelected = MutableLiveData(false)
    var isNewLocationSelected : MutableLiveData<Boolean> = _isNewLocationSelected

    private var _selectedLat = mutableStateOf(0.0)
    var selectedLat : MutableState<Double> = _selectedLat


    private var _selectedLng = mutableStateOf(0.0)
    var selectedLng : MutableState<Double> = _selectedLng


    private var _userCurrentLng = mutableStateOf(0.0)
    var userCurrentLng : MutableState<Double> = _userCurrentLng

    private var _userCurrentLat = mutableStateOf(0.0)
    var userCurrentLat : MutableState<Double> = _userCurrentLat

    val pickUp = LatLng(userCurrentLat.value, userCurrentLng.value)

    private var _locationPermissionGranted = MutableLiveData(false)
    var locationPermissionGranted : LiveData<Boolean> = _locationPermissionGranted

    fun currentUserGeoCOord(latLng: LatLng){
        _userCurrentLat.value = latLng.latitude
        _userCurrentLng.value = latLng.longitude
    }

    fun updateSelectedLocation(status: Boolean){
        _isNewLocationSelected.value = status
    }

    fun getSelectedLocation(latLng: LatLng){
        _selectedLat.value = latLng.latitude
        _selectedLng.value = latLng.longitude
    }

    fun permissionGrand(setGranted: Boolean){
        _locationPermissionGranted.value = setGranted
    }

}