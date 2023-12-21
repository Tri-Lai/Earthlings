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


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.earthlings.data.Validator
import com.example.earthlings.model.Screen
import com.google.firebase.auth.FirebaseAuth


/**
 * LoginViewModel is used to handle login function (e.g. login, logout, validate credentials format)
 */
class LoginViewModel(
    private val navHostController: NavHostController
) : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)


    // Listen for any changes on email, password fields and login ,logout button
    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> { login() }
            is LoginUIEvent.LogoutButtonClicked -> { logout() }

            else -> {}
        }

        // Validate format of password and email
        validateLoginUIDataWithRules()
    }


    /**
     * Validate format of password and email
     */
    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        // Check email and password
        allValidationsPassed.value = emailResult.status && passwordResult.status
    }


    /**
     * Login with valid credentials and go to Homepage as soon as
     * login success
     */
    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"${it.isSuccessful}")
                if(it.isSuccessful){

                    // Turn off loading circle
                    loginInProgress.value = false

                    // Go to Homepage
                    navHostController.navigate(Screen.HomeScreen.route)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, it.localizedMessage)
                loginInProgress.value = false
            }
    }

    /**
     * Logout of current session
     */
    private fun logout() {

        // Get Firebase Authentication instance
        val firebaseAuth = FirebaseAuth.getInstance()

        // Sign out
        firebaseAuth.signOut()

        // Check if sign out success
        val authStateListener = FirebaseAuth.AuthStateListener {

            // Sign out success
            if (it.currentUser == null) {

                Log.d("Auth", "Sign out success")

                // Back to login page
//                navHostController.navigate(Screen.Register.Login.route) {
//                    launchSingleTop = true
//                    popUpTo(Screen.Register.Login.route)
//                }

            } else {
                Log.d("Auth", "Sign out fail")
            }
        }

        firebaseAuth.addAuthStateListener { authStateListener }
    }
}


/**
 * LoginViewModelFactory class is used to inject the NavHostController dependency
 * into LoginViewModel
 */
class LoginViewModelFactory(private val navHostController: NavHostController): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(navHostController = navHostController) as T
    }
}
