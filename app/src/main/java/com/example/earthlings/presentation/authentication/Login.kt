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


package com.example.earthlings.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.earthlings.R
import com.example.earthlings.data.login.LoginUIEvent
import com.example.earthlings.data.login.LoginViewModel
import com.example.earthlings.data.login.LoginViewModelFactory
import com.example.earthlings.model.Screen
import com.example.earthlings.presentation.authentication.components.ButtonComponent
import com.example.earthlings.presentation.authentication.components.CustomTextField
import com.example.earthlings.presentation.authentication.components.DividerComponent
import com.example.earthlings.presentation.authentication.components.PasswordTextField
import com.example.earthlings.presentation.authentication.components.TextButton
import com.example.earthlings.presentation.common.NormalText
import com.example.earthlings.presentation.common.TitleText

@Composable
fun Login(
    navHostController: NavHostController
) {

    var loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(navHostController = navHostController))

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Image(painter = painterResource(
                    id = R.drawable.logo_black),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(200.dp))

                Spacer(modifier = Modifier.height(20.dp))

                TitleText(value = stringResource(id = R.string.login_title))

                NormalText(value = stringResource(id = R.string.login_desc))

                Spacer(modifier = Modifier.height(20.dp))

                CustomTextField(
                    labelValue = stringResource(id = R.string.email),
                    painterResource(id = R.drawable.baseline_person_24),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                Spacer(modifier = Modifier.height(20.dp))

                PasswordTextField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.baseline_lock_24),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(value = "Forgot password ?")

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                DividerComponent()

                val authenText = "Don’t have an account yet? "
                val annotatedString = buildAnnotatedString {
                    append(authenText)
                    withStyle(style = SpanStyle(color = Color(0,136,204))) {
                        pushStringAnnotation(tag = "Signup", annotation = "Signup")
                        append("Signup")
                    }
                }

                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 40.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                        color = Color(151,151,151)
                    ),
                    text = annotatedString,
                    onClick = {
                        navHostController.navigate(Screen.Register.Signup.route)
                    }
                )
            }
        }

        if (loginViewModel.loginInProgress.value)
            CircularProgressIndicator()
    }
}
