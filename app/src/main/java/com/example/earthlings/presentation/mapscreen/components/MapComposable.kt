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


package com.example.earthlings.presentation.mapscreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.earthlings.model.EventData
import com.example.earthlings.model.MarkerInfo
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapComposable(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    userLocation: MutableState<LatLng?>,
    isInitialLocationSet: MutableState<Boolean>,
    markers: List<MarkerInfo>,
    markerIcons: SnapshotStateMap<LatLng, BitmapDescriptor>,
    isEventCardOn: Boolean,
    eventCardInfo: EventData,
    onBackButtonClick: () -> Unit,
    onEventCardClick: () -> Unit
) {
    Box {
        GoogleMap(
            modifier = modifier.height(500.dp).fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(zoomControlsEnabled = true)
        ) {
            userLocation.value?.let {
                if (!isInitialLocationSet.value) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(it, 18f) // Closer zoom level
                    isInitialLocationSet.value = true
                }
            }
            markers.forEach { marker ->
                markerIcons[marker.latLng]?.let {
                    Marker(
                        MarkerState(position = marker.latLng), icon = it
                    )
                }
            }
        }
    }
}