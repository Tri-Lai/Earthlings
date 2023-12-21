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


package com.example.earthlings.presentation.homescreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.earthlings.data.DataViewModel
import com.example.earthlings.model.FilterChipData
import com.example.earthlings.model.StatData
import com.example.earthlings.presentation.add_form.AddSiteScreen
import com.example.earthlings.presentation.add_form.toFormattedString
import com.example.earthlings.presentation.common.CenterActionButton
import com.example.earthlings.presentation.common.FilterChipsWithEmoji
import com.example.earthlings.presentation.common.NavBar
import com.example.earthlings.presentation.mapscreen.MapScreen
import com.example.earthlings.presentation.searchscreen.SearchBar
import com.example.earthlings.presentation.searchscreen.SearchResults
import com.example.earthlings.presentation.setting.Setting
import com.example.earthlings.presentation.statscreen.StatScreen
import com.google.firebase.auth.FirebaseAuth
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(dataViewModel: DataViewModel = viewModel()) {
    val navController = rememberNavController()

    // Get current user session
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid

    // Event list for storing fetching data
    val eventsList = dataViewModel.eventsList.value

    // Get upcoming event
    var upcomingEvent = remember {
        mutableStateOf(eventsList.filter {
            it.eventTime > Date().toFormattedString()
        })
    }

    Scaffold(
        bottomBar = { NavBar(navController = navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButton = { CenterActionButton(navController = navController) },
        floatingActionButtonPosition = FabPosition.Center,

        ) {
        //
        NavHost(navController, startDestination = "home") {
            // Home screen
            composable("home") {
                // Your existing Home screen content
                HomeContent()
            }

            // Add site form
            composable("addBooking") {
                AddSiteScreen(navHostController = navController)
            }

            // Maps Screen
            composable("maps") {
                Log.e("Map", "${eventsList.size}")
                MapScreen(inputEventData = eventsList)
            }

            // Setting screen
            composable("settings") {
                Setting(navController = navController)
            }

            // Dashboard Screen
            composable("dashboard") {
                if (userID != null) {
                    StatScreen(
                        inputData = StatData(
                            eventsList.size,
                            upcomingEvent.value.size
                        )
                    )
                }
            }
        }
    }
}

//TODO: Configuring routing strategy
@Composable
fun HomeContent() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var filterOptions = listOf(
        FilterChipData("🌊", "Beach"),
        FilterChipData("🏕️", "Forest"),
        FilterChipData("👨‍👩‍👧‍👦", "Popular"),
        FilterChipData("🏕️", "City"),
        FilterChipData("⭐️", "Trending"),
        FilterChipData("🥬", "Community"),
        FilterChipData("🏫", "School")
    )
    var selectedFilter by remember { mutableStateOf(filterOptions[0]) }

    Column {
        SearchBar(searchText, onSearchTextChanged = { searchText = it })
        FilterChipsWithEmoji(
            filterOptions = filterOptions,
            selectedFilter = selectedFilter.category
        ) { selectedFilter.category = it }
        SearchResults(searchText.text, selectedFilter.category)
    }
}

enum class Screen(val route: String, val icon: ImageVector, val title: String) {
    Home("home", Icons.Default.Home, "Home"),
    Profile("profile", Icons.Default.Person, "Profile"),
    Settings("settings", Icons.Default.Settings, "Settings")
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

