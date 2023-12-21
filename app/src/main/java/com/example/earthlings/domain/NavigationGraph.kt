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


package com.example.earthlings.domain

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.earthlings.model.Screen
import com.example.earthlings.presentation.authentication.Login
import com.example.earthlings.presentation.authentication.Signup
import com.example.earthlings.presentation.homescreen.HomeScreen
import com.example.earthlings.presentation.onboarding.OnboardingScreen

/**
 * Create navigation link to screens
 */
@Composable
fun NavigationGraph() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Register.route
    ) {

        // Onboarding Screen
        composable(Screen.OnboardingScreen.route) {
            OnboardingScreen(navHostController = navHostController)
        }

        navigation(
            route = Screen.Register.route,
            startDestination = Screen.Register.Login.route
        ) {

            // Login
            composable(Screen.Register.Login.route) {
                Login(navHostController = navHostController)
            }

            // Signup
            composable(Screen.Register.Signup.route) {
                Signup(navHostController = navHostController)
            }
        }

        // Home Screen
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}