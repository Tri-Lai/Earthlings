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


package com.example.earthlings.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.earthlings.R

data class Page(
    val title: String,
    val desc: String,
    @DrawableRes val image: Int
)

val pages = listOf<Page>(
    Page(
        "Lorem ipsum is simply dummy 1",
        "Lorem ipsum is simply dummy text of the printing and typesetting inddustry",
         R.drawable.onboarding1
    ),
    Page(
        "Lorem ipsum is simply dummy 2",
        "Lorem ipsum is simply dummy text of the printing and typesetting inddustry",
        R.drawable.onboarding2
    ),
    Page(
        "Lorem ipsum is simply dummy 3",
        "Lorem ipsum is simply dummy text of the printing and typesetting inddustry",
        R.drawable.onboarding3
    )
)
