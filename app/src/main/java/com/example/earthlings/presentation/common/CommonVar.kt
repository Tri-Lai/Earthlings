package com.example.earthlings.presentation.common

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.earthlings.R
import com.example.earthlings.model.MarkerInfo
import com.google.android.gms.maps.model.LatLng

sealed class CommonVar {
    companion object {

        val placeHolderImageURL: String =
            "https://source.unsplash.com/random/970x646/?volunteer%20outdoor%20activity"
        val placeHolderAvatarImageURL: String =
            "https://source.unsplash.com/random/970x646/?example%20person%20profile"
        val placeHolderStatImageURL: String =
            "https://img.freepik.com/free-vector/progress-overview-concept-illustration_114360-5262.jpg"
        val placeHolderAboutUsImage: String =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTagisXNHdeWqXgu5FIpjLoDac-WphknlleFUIDEyMTGOc0-RD8iR7tEKPVYOM&s"
        val sampleMarkerVal = listOf(
            MarkerInfo(LatLng(51.508610, -0.163611), placeHolderImageURL),
            MarkerInfo(LatLng(51.5074, -0.1278), placeHolderImageURL)
            // Add more markers as needed
        )
    }
}