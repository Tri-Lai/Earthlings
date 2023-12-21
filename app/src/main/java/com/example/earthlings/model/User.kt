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



package com.example.earthlings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * User data model
 */
data class User (
    var userID: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val username: String = "",
    val email: String = "",
    val image: String = "",
    val attendActivity: Int = 0,
    val membership: String = "normal",
    val userType: String = "normal"
)