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


package com.example.earthlings.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.earthlings.R
import com.example.earthlings.data.login.LoginUIEvent
import com.example.earthlings.data.login.LoginViewModel
import com.example.earthlings.data.login.LoginViewModelFactory
import com.example.earthlings.model.User
import com.example.earthlings.presentation.authentication.components.ButtonComponent
import com.example.earthlings.presentation.common.Avatar
import com.example.earthlings.presentation.common.TitleText
import com.example.earthlings.presentation.setting.components.AttendedActivities
import com.example.earthlings.presentation.setting.components.Membership
import com.example.earthlings.presentation.setting.components.UserType
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

@Composable
fun Setting(
    navController: NavHostController
) {
    val auth = FirebaseAuth.getInstance()

    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(navHostController = navController))


    val userID = auth.currentUser?.uid
    var userData by remember(userID) {
        mutableStateOf(User())
    }

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

    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.Magenta)

    Surface(color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
         Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            TitleText(value = stringResource(id = R.string.setting))

            Spacer(modifier = Modifier.height(10.dp))

            Avatar(
                imageUrl = userData.image,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${userData.firstname} ${userData.lastname}",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            UserType(type = userData.userType)

            Spacer(modifier = Modifier.height(20.dp))

            // User's tier and Attended activities
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Membership(tier = userData.membership)

                Spacer(modifier = Modifier.width(40.dp))

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(60.dp)
                        .width(2.dp)
                )

                Spacer(modifier = Modifier.width(40.dp))

                AttendedActivities(attendActivitiesNo =  userData.attendActivity.toString())
            }

            Spacer(modifier = Modifier.height(30.dp))

            ButtonComponent(
                value = "My dashboard",
                onButtonClicked = {
                    navController.navigate("dashboard")
                },
                isEnabled = true)

             Spacer(modifier = Modifier.height(30.dp))

             ButtonComponent(
                value = "Logout",
                onButtonClicked = {
                    loginViewModel.onEvent(LoginUIEvent.LogoutButtonClicked)
                },
                isEnabled = true)

            Spacer(modifier = Modifier.height(100.dp))

        }
    }
}


//@Preview
//@Composable
//fun PreviewSetting() {
//    Setting()
//}