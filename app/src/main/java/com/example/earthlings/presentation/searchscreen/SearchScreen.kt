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

package com.example.earthlings.presentation.searchscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.earthlings.data.DataViewModel
import com.example.earthlings.model.EventData
import com.example.earthlings.model.nearMe
import com.example.earthlings.model.popularEvents
import com.example.earthlings.model.sampleEvents
import com.example.earthlings.presentation.common.CommonVar
import com.example.earthlings.presentation.common.HorizontalHomeCard
import com.example.earthlings.presentation.homescreen.component.PopularEvents
import com.example.earthlings.presentation.homescreen.component.SectionHeader
import com.example.earthlings.presentation.searchscreen.component.MyLocationButton

@Composable
@Preview
fun SearchResultsScreen(eventDetails: List<EventData> = sampleEvents) {
    LazyColumn {
        item {
            //TODO: Pass a call back function to handle the map and stuff in this function
            MyLocationButton()
        }
        //Render a lít of items
        items(eventDetails.size) { items ->
            //Get the element of the event details
            val res = eventDetails[items]
            //Display to the card
            HorizontalHomeCard(
                imageUrl = res.imageUrl,
                eventTitle = res.eventName,
                eventLocation = res.location
            ) {

            }
        }
    }
}


@Composable
fun SearchBar(searchText: TextFieldValue, onSearchTextChanged: (TextFieldValue) -> Unit) {
    // Add the location information in herer to
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(modifier = Modifier.padding(start = 10.dp)) {
            //Reverse GEO CODE OF Tis
            Icon(
                Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.padding(top = 6.dp, end = 4.dp)
            )
            //TODO: Need a state to manage the current location define a model
            Text(
                text = "San Francisco",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        TextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, "Search Icon") },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Search here...") }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun SearchResults(searchText: String = "AD",
                  selectedFilter: String = "",
                  dataViewModel: DataViewModel = viewModel()) {

    // Event list for storing fetching data
    val eventsList = dataViewModel.eventsList.value

    Log.d("EventList", "${eventsList.size}")

    // Coroutine scope for asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    // Check data loading
    val isLoading = remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.padding(16.dp)) {
        //TODO: Need a function to fetch the data and state to manage that
        if (searchText.isNotEmpty()) {
            // Display filtered results based on searchText and selectedFilter
            Column {
                //Have a if to check if filter is selected
                //Have a list if filter not found
                Text(text = "Search result for ${searchText} + filter ${selectedFilter}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                SearchResultsScreen()
            }
            // Here you would query your data source based on the search text and selected filter
            // and display the results.
        } else {
            if (isLoading.value) {
                Text(text = "Loading data...")
            } else {
                LazyColumn {
                    // Section for "Near Me" events
                    item {
                        SectionHeader(title = "Near Me")
                    }
                    items(nearMe.size) { index ->
                        val event = nearMe[index]
                        com.example.earthlings.presentation.homescreen.component.HorizontalHomeCard(
                            imageUrl = event.imageUrl,
                            eventTitle = event.eventName,
                            eventLocation = event.location
                        ) {
                            // Add action for card click here
                        }
                    }


                    // Section for "Popular" events with a horizontal scroll
                    item {
                        SectionHeader(title = "Popular")
                        PopularEvents(popularEvents)
                    }

                    // Section for general events
                    item {
                        SectionHeader(title = "Events")
                    }
                    items(eventsList.size) { index ->
                        val event = eventsList[index]
                        HorizontalHomeCard(
                            imageUrl = event.imageUrl,
                            eventTitle = event.eventName,
                            eventLocation = event.location
                        ) {
                            // Add action for card click here
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(100.dp))
                    }
                }
            }
        }
    }
}
