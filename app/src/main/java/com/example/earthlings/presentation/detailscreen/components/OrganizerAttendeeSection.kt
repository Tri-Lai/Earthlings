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


package com.example.earthlings.presentation.detailscreen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.earthlings.presentation.common.Avatar
import com.example.earthlings.presentation.common.CommonVar

@Composable
fun OrganizerAttendees(imageUrl: String, participantCount: String) {
    Column(modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp)) {
        Text(
            text = "Organizers & Attendees",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Avatar(imageUrl = imageUrl) // Use 'profileImageUrl' parameter again for attendee avatars
            Spacer(modifier = Modifier.width(.25.dp))
            Text(
                participantCount, color = Color.White, modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Wade Warren", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* TODO: Implement onClick action */ }) {
                Icon(
                    Icons.Default.Message,
                    contentDescription = "Message Icon",
                    tint = Color.Gray // Use your theme's color
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrganizerAttendeesUI() {
    OrganizerAttendees("", "+84")
}