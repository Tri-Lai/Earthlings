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


package com.example.earthlings.data.login

/**
 * LoginUIState check validated format of email and password field
 */
data class LoginUIState(
    var email  :String = "",
    var password  :String = "",
    var emailError :Boolean = false,
    var passwordError : Boolean = false
)