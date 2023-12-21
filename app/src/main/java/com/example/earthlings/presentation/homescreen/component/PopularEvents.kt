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



package com.example.earthlings.presentation.homescreen.component

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import com.example.earthlings.model.EventData

@Composable
fun PopularEvents(events: List<EventData>) {
    LazyRow {
        items(events.size) { index ->
            val event = events[index]
            VerticalHomeCard(
                tag = event.tag,
                imageUrl = event.imageUrl,
//                profileImageUrl = event.profileImageUrl,
                profileImageUrl = "",
                organizerName = event.organizerName,
                eventName = event.eventName,
                eventTime = event.eventTime,
                attendeeCount = event.attendeeCount.toString()
            ) {
                // Add action for card click here
            }
        }
    }
}