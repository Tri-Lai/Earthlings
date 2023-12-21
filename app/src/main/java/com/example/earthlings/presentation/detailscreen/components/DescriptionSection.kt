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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.earthlings.presentation.common.CommonVar

@Composable
@Preview
fun DescriptionSection(
    aboutText: String = "We have a team but still missing a couple of peopleWe have a team but still missing a couple\n" +
            "of people. Let's play together! We have a\n" +
            "team but still missing a couple of people.\n" +
            "We have a team but still missing a couple\n" +
            "of people",
    inputAvatarUrl: String = "",
    participantCount: String = "+14"
) {
    //Check if avatar is available not, render URL
    var avatarUrl =
        if (inputAvatarUrl != "") inputAvatarUrl else CommonVar.placeHolderImageURL
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Text(
                text = "Description",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                fontSize = 26.sp
            )
            Text(
                text = aboutText,
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp, end = 15.dp),
                style = MaterialTheme.typography.body1
            )
            OrganizerAttendees(imageUrl = avatarUrl, participantCount = participantCount)
        }
    }
}
