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


package com.example.earthlings.data

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.earthlings.model.EventData
import com.example.earthlings.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

/**
 * DataViewModel is used to manage database accessibility
 */
class DataViewModel: ViewModel() {
    // Temp fetching data
    private val tempFetchData = mutableStateOf(listOf<EventData>())

    // Event list for storing fetching data
    val eventsList: State<List<EventData>> = tempFetchData

    // Retrieve events once view model is instantiated
    init {
        getAllEvents { dataList ->
            tempFetchData.value = dataList
            Log.d("firestore", "GET FORM DATA SUCCESS: ${eventsList.value.size}")
        }
    }


    /**
     * Save form for creating new site
     */
    fun saveForm(
        tag: String,
        image: Uri?,
        organizerName: String,
        eventName: String,
        eventTime: String,
        location: String,
        attendeeCount: Int,
        description: String
    ) {

        // Create a storage reference
        val storage = FirebaseStorage.getInstance()
            .reference
            .child("sites/${UUID.randomUUID()}.jpg")

        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()

        var imgUrl: String

        // Store site image to Firebase Storage
        // and update user details once the form is submitted
        image?.let {

            // Store site image on Firebase Storage
            storage.putFile(it)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        // Download the full path of image url on Storage
                        // to save its link on FireStore
                        storage.downloadUrl.addOnSuccessListener { uri ->
                            imgUrl = uri.toString()

                            // Create EventData instance
                            val formData = EventData(
                                tag = tag,
                                imageUrl =  imgUrl,
                                organizerName =  organizerName,
                                eventName = eventName,
                                eventTime = eventTime,
                                location = location,
                                attendeeCount =  attendeeCount,
                                description = description)

                            // Add newly created form to FireStore
                            db.collection("form")
                                .add(formData)
                                .addOnSuccessListener {

                                    // Update user attend activity N.O
                                    updateUserDetails()

                                    Log.d("Firestore", "Save form successfully")
                                }
                                .addOnFailureListener{ it ->
                                    Log.d("Firestore", "Exception= ${it.message}")
                                    Log.d("Firestore", "Exception= ${it.localizedMessage}")
                                }
                        }
                    } else {
                        Log.d("Storage", "Upload failed: ${task.exception?.message}")
                    }
                }
        }
    }

    /**
     * Get user details
     */
    fun getUserDetails(): User? {

        // Get current session of user by Firebase Authentication
        val auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid

        // Get FireStore instance
        val db = Firebase.firestore

        val TAG = "Firestore"
        var userData: User? = null

        // If user is found
        if (userID != null) {
            val userDocument = db.collection("user").document(userID)

            userDocument
                .get()
                .addOnSuccessListener {
                    // Get user data
                    userData = if (it.exists()) it.toObject(User::class.java) else null
                }
                .addOnFailureListener {
                    Log.e(TAG, "Failed to retrieve user.")
                }

            return userData
        }
        return null
    }


    /**
     * Update user details
     */
    fun updateUserDetails() {

        // Get current session of user by Firebase Authentication
        val auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid

        // Get FireStore instance
        val db = Firebase.firestore

        val TAG = "Firestore"

        // If user is found
        if (userID != null) {
            val userDocument = db.collection("user").document(userID)
            userDocument
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        // Get user data
                        val userData = it.toObject(User::class.java)

                        // Update organiser's attend activity n.o by 1
                        var currentAttendActivity = userData!!.attendActivity
                        currentAttendActivity += 1

                        // Update on FireStore
                        userDocument
                            .update("attendActivity", currentAttendActivity)
                            .addOnSuccessListener {
                                Log.d(TAG, "attendActivity successfully updated!")
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Error updating attendActivity", e)
                            }
                    } else {
                        Log.e(TAG, "No user found")
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Failed to retrieve user $userID")
                }
        }
    }

    /**
     * Get all events on FireStore
     */
    fun getAllEvents(
        onResult: (List<EventData>) -> Unit
    ) {
        // Create a FireStore reference
        val db = FirebaseFirestore.getInstance()
        val TAG = "firestore"

        db.collection("form")
            .get()
            .addOnSuccessListener {snapshot ->
                // Create empty list
                val eventList = mutableListOf<EventData>()

                // Retrieve events on FireStore to local
                for (document in snapshot) {
                    val event = document.toObject(EventData::class.java)
                    eventList.add(event)
                }

                onResult(eventList)
                Log.e(TAG, "Get all event data success")
            }
            .addOnFailureListener {e ->
                onResult(emptyList())
                Log.e(TAG, "Failed to get event data: ${e.message}")
            }
    }
}