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



package com.example.earthlings.presentation.detailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.earthlings.presentation.common.CommonVar
import com.example.earthlings.presentation.detailscreen.components.DescriptionSection
import com.example.earthlings.presentation.detailscreen.components.DetailViewScreenCard
import com.example.earthlings.presentation.detailscreen.components.JoinNowBtn

@Composable
@Preview
fun DetailScreen() {
    Scaffold(
        bottomBar = { JoinNowBtn {

        } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopSection()
            Spacer(modifier = Modifier.height(50.dp))
            //TODO: Inject data here
            DescriptionSection()
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun TopSection(inputImageUrl: String = "") {
    val imageUrl =
        if (inputImageUrl != "") inputImageUrl else CommonVar.placeHolderImageURL
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            imageUrl,
            contentDescription = "Event Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        DetailViewScreenCard(
            imageUrl = "",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = 60.dp),
            title = "Party with friends at night - 2022",
            organizer = "Gandhinagaer",
            participantCount = "40+"
        )
        // Add your back arrow icon and event details here.
    }
}

@Composable
fun LocationSection() {
    // This would ideally be a GoogleMap composable from google-maps-compose library.
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray) // Placeholder for the map
    ) {
        // Add your map composable or an image here.
    }
}


