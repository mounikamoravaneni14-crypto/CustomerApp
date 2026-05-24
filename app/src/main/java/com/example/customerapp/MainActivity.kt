package com.example.customerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.customerapp.model.RegistrationResponse
import com.example.customerapp.ui.theme.CustomerAppTheme
import com.example.customerapp.ui.theme.registration.RegistrationScreen
import com.example.customerapp.ui.theme.screens.SuccessScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomerAppTheme {

                var registrationData by remember {
                    mutableStateOf<RegistrationResponse?>(null)
                }

                var showSuccessScreen by remember {
                    mutableStateOf(false)
                }

                if (showSuccessScreen) {

                    SuccessScreen(
                        registrationResponse = registrationData,
                        onBackClick = {
                            showSuccessScreen = false
                        }
                    )

                } else {

                    RegistrationScreen(
                        onNavigateSuccessScreen = { response ->

                            registrationData = response

                            showSuccessScreen = true
                        }
                    )
                }
            }
        }

    }
}
