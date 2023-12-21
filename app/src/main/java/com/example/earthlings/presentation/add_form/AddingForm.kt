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


package com.example.earthlings.presentation.add_form

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.earthlings.data.DataViewModel
import com.example.earthlings.model.User
import com.example.earthlings.presentation.common.Dialog
import com.example.earthlings.presentation.homescreen.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


var imageUri: Uri? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSiteScreen(
    navHostController: NavHostController
) {

    var dataViewModel = DataViewModel()

    // Site details variables
    var siteLocation by rememberSaveable { mutableStateOf("") }
    var eventName by rememberSaveable { mutableStateOf("") }
    var organizerName by rememberSaveable { mutableStateOf("") }
    var attendeeCount by rememberSaveable { mutableStateOf("") }
    var tag by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var eventTime by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    var isExpanded by remember { mutableStateOf(false) }

    // Remember scroll state
    val scrollState = rememberScrollState()

    // Dialog for knowing action result
    var showDialog = remember { mutableStateOf(false) }
    var formSubmittedResult = remember { mutableStateOf("") }
    var resultDesc = remember { mutableStateOf("") }

    // Get user details
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var userData by remember(userID) {
        mutableStateOf(User())
    }

    // Fetch user from FireStore
    LaunchedEffect(userID) {
        if (userID != null) {
            val userRef = Firebase.firestore.collection("user").document(userID)
            val snapshot = userRef.get().await()

            snapshot?.let {
                snapshot.toObject<User>()?.let {
                    userData = it
                }
            }
        }
    }

    // ---- UI Layouts ----

    // Show dialog after submitted form
    if (showDialog.value) {
        Dialog(
            title = formSubmittedResult.toString(),
            desc = resultDesc.toString(),
            onDismiss = {
                showDialog.value = false
            })
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp, 16.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text (
            text = "Add Site",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Column {
            ImagePicker()

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Site Location",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = siteLocation,
                onValueChange = { siteLocation = it },
                placeholder = {
                    Text(text = "New York")
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Event name",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = eventName,
                onValueChange = { eventName = it },
                placeholder = {
                    Text(text = "e.g. City Clean-up Drive")
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EventTime{ eventTime = it }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Tag",
                style = MaterialTheme.typography.bodyLarge
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }
                ) {
                    TextField(
                        value = tag,
                        onValueChange =  {},
                        readOnly = true,
                        trailingIcon =  {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Beach") },
                            onClick = {
                                tag = "Beach"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Forest") },
                            onClick = {
                                tag = "Forest"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Popular") },
                            onClick = {
                                tag = "Popular"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "City") },
                            onClick = {
                                tag = "City"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Trending") },
                            onClick = {
                                tag = "Trending"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Community") },
                            onClick = {
                                tag = "Community"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "School") },
                            onClick = {
                                tag = "School"
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Attendee number expected",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = attendeeCount,
                onValueChange = { attendeeCount = it },
                placeholder = {
                    Text(text = "e.g. 1OO")
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Site description",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(text = "e.g. Join us in making our city cleaner and greener. This event focuses on cleaning up local parks and streets.")
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = {
                    dataViewModel
                        .saveForm(
                            location = siteLocation,
                            eventName = eventName,
                            eventTime = eventTime,
                            tag = tag,
                            attendeeCount = attendeeCount.toInt(),
                            description = description,
                            image = imageUri,
                            organizerName = userData.username
                        )

                    formSubmittedResult.value = "Submitted"
                    resultDesc.value = "New site information has been created successfully."
                    showDialog.value = true

                    navHostController.navigate(Screen.Home.route)
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.padding(50.dp))

        }
    }
}

/**
 * Create time picker with Calender
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTime(endDate: (String) -> Unit) {
    Text(
        text = "Event time",
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val currentDate = Date().toFormattedString()
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog =
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            val formattedDate = formatDate(newDate.timeInMillis)
            selectedDate = formattedDate
            endDate(formattedDate)
        }, year, month, day)

    TextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource
    )

    if (isPressed) {
        datePickerDialog.show()
    }
}

/**
 * Create image picker
 */
@Composable
fun ImagePicker(maxSelection: Int = 1) {
    var selectedImages by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }

    val context = LocalContext.current

    val selectBtn = if (maxSelection > 1) {
        "Select up to $maxSelection photos"
    } else {
        "Select a photo"
    }

    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImages = listOf(uri) }
    )

    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = if (maxSelection > 1) maxSelection else 2
        ),
        onResult = { uris -> selectedImages = uris }
    )

    fun launchPhotoPicker() {
        if (maxSelection > 1) {
            multipleImagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            singleImagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLayoutView(selectedImages = selectedImages)

        Button(onClick = {
            launchPhotoPicker()
        }) {
            Text(selectBtn)
        }

        Column {
            selectedImages.forEach { uri ->
                uri?.let {
                    imageUri = it
                    Text(text = context.getImageName(it))
                }
            }
        }
    }
}

fun Context.getImageName(uri: Uri): String {
    var imageName = ""
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            imageName = it.getString(nameIndex)
        }
    }
    return imageName
}

/**
 * Define site image layout
 */
@Composable
fun ImageLayoutView(selectedImages: List<Uri?>) {
    LazyRow {
        items(selectedImages) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Site image",
                modifier = Modifier
                    .height(300.dp)
                    .width(400.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }
}




fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}


fun formatDate(timeInMillis: Long): String {
    val formatter = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timeInMillis))
}
