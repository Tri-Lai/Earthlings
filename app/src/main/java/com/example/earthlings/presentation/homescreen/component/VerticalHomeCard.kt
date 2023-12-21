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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.earthlings.presentation.common.Avatar
import com.example.earthlings.presentation.common.CommonVar


@Composable
fun VerticalHomeCard(
    tag: String,
    imageUrl: String,
    profileImageUrl: String,
    organizerName: String,
    eventName: String,
    eventTime: String,
    attendeeCount: String,
    onActionClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 9.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = if (imageUrl != "") imageUrl else CommonVar.placeHolderImageURL, // Use 'imageUrl' parameter
                    contentDescription = "Event",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Avatar(imageUrl = profileImageUrl) // Use 'profileImageUrl' parameter
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    organizerName,
                    fontWeight = FontWeight.Bold
                ) // Use 'organizerName' parameter
            }
            Column(modifier = Modifier.padding(3.dp)) {
                Text(
                    eventName, // Use 'eventName' parameter
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    eventTime, // Use 'eventTime' parameter
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(imageUrl = profileImageUrl) // Use 'profileImageUrl' parameter again for attendee avatars
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    attendeeCount, color = Color.White, modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onActionClick, // Use 'onActionClick' parameter
                    //TODO: Could change color right here
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text("Join Now", color = Color.White) // Use 'actionText' parameter
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VerticalEventCardPreview() {
    VerticalHomeCard(
        tag = "Dance",
        imageUrl = "https://images.unsplash.com/photo-1579353977828-2a4eab540b9a?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2FtcGxlfGVufDB8fDB8fHww",
        profileImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/John_Legend_2019_by_Glenn_Francis_%28cropped%29.jpg/440px-John_Legend_2019_by_Glenn_Francis_%28cropped%29.jpg",
        organizerName = "Altanito Salami",
        eventName = "Cleaning Up Event",
        eventTime = "THU 26 May, 09:00 - FRI 27 May, 10:00",
        attendeeCount = "+15",
        onActionClick = { /* Handle action click */ }
    )
}