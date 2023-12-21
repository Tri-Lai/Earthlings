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


package com.example.earthlings.presentation.mapscreen.components;

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

object LocationUtils {

    // Fetch the last known location
    @SuppressLint("MissingPermission")
    fun Context.fetchLastLocation(
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let { locationCallback.onLocationResult(LocationResult.create(listOf(it))) }
        }
    }

    /**
     * Subscribe to location updates
     */
    @SuppressLint("MissingPermission")
    fun subscribeToLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback,
        locationUpdateInterval: Long
    ) {
        val locationRequest = LocationRequest.create().apply {
            interval = locationUpdateInterval
            fastestInterval = locationUpdateInterval / 2
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}
