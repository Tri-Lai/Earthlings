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


package com.example.earthlings.presentation.mapscreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.earthlings.domain.location_services.geocodeAddress
import com.example.earthlings.model.EventData
import com.example.earthlings.model.MarkerInfo
import com.example.earthlings.model.popularEvents
import com.example.earthlings.model.sampleEvents
import com.example.earthlings.presentation.common.CommonVar
import com.example.earthlings.presentation.mapscreen.components.LocationUtils
import com.example.earthlings.presentation.mapscreen.components.MapComposable
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

// Function to create a circular bitmap
fun Bitmap.toSmallRoundBitmap(): Bitmap {
    val size = 120 // Desired size in pixels
    val scaledBitmap = Bitmap.createScaledBitmap(this, size, size, false)
    val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(output)
    val paint = Paint()
    val shader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

    paint.isAntiAlias = true
    paint.shader = shader

    val radius = size / 2f
    canvas.drawCircle(radius, radius, radius, paint)

    return output
}

@SuppressLint("RememberReturnType")
@Composable
@Preview(showBackground = true)
fun MapScreen(
    modifier: Modifier = Modifier.fillMaxWidth(),
    inputEventData: List<EventData> = popularEvents
) {
    //Set initial position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 17f)
    }
    val context = LocalContext.current
    val isInitialLocationSet =
        remember { mutableStateOf(false) } // To track if the initial location is set
    val markerIcons = remember { mutableStateMapOf<LatLng, BitmapDescriptor>() }

    var markers: List<MarkerInfo> = listOf()

    //Loop through end encode geo
    inputEventData.forEach {
        Log.i("it", "$it")
        val imageUrl = if (it.imageUrl != "") it.imageUrl else CommonVar.placeHolderImageURL
        markers = markers.plus(
            MarkerInfo(
                LatLng(0.0, 0.0),
                imageUrl,
                it.formID
            )
        )
    }

    Log.i("Markers", "$markers")

    markers.forEach { marker ->
        LaunchedEffect(marker) {
            val latlng = marker.formID?.let { searchElement(it, inputEventData) }?.let {
                geocodeAddress(
                    context,
                    it
                )
            }

            Log.e("LatLong", "$latlng")

            if (latlng != null) {
                marker.latLng = latlng
            }
            Log.i("LatLong", "$latlng")
            val imageLoader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(marker.imageUrl)
                .allowHardware(false)
                .build()
            val result = imageLoader.execute(request).drawable
            result?.toBitmap()?.let { bitmap ->
                val roundBitmap = bitmap.toSmallRoundBitmap()
                markerIcons[marker.latLng] = BitmapDescriptorFactory.fromBitmap(roundBitmap)
            }
        }
    }
    val userLocation = remember { mutableStateOf<LatLng?>(null) }

    // Define the location update interval (e.g., 5000 milliseconds for 5 seconds)
    val locationUpdateInterval = 5000L
    DisposableEffect(key1 = Unit) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    userLocation.value = LatLng(location.latitude, location.longitude)

                }
            }
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        LocationUtils.subscribeToLocationUpdates(
            fusedLocationClient = fusedLocationClient,
            locationCallback = locationCallback,
            locationUpdateInterval = locationUpdateInterval
        )

        // Unsubscribe when the effect leaves the composition
        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    //Composable element

    MapComposable(
        modifier,
        cameraPositionState,
        userLocation,
        isInitialLocationSet,
        markers,
        markerIcons,
        isEventCardOn = true,
        sampleEvents[0],
        {},
        {}
    )

}

fun searchElement(formID: String, inputList: List<EventData>): String {
    var res = ""
    //Iterate through a list of eventdata
    inputList.forEach {
        res = if (it.formID!!.let { it1 -> formID.contains(it1) }) it.location else res
    }
    Log.i("Search Element", res)
    return res
}