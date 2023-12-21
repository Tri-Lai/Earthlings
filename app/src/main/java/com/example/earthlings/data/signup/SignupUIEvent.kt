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


package com.example.earthlings.data.signup

/**
 * SignupUIEvent class is used to detect for any changes on
 * last name, first name, username, user type, email and password and
 * register button
 */
sealed class SignupUIEvent {
    data class LastNameChanged(val lastname: String): SignupUIEvent()
    data class FirstNameChanged(val firstname: String): SignupUIEvent()
    data class UsernameChanged(val username: String): SignupUIEvent()
    data class UserTypeChanged(val userType: String): SignupUIEvent()
    data class EmailChanged(val email: String): SignupUIEvent()
    data class PasswordChanged(val password: String): SignupUIEvent()

    object RegisterButtonClicked: SignupUIEvent()
}