package com.example.customerapp.ui.theme.registration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.customerapp.model.RegistrationResponse
import com.example.customerapp.model.RegistrationUIState
import com.example.customerapp.ui.theme.components.CustomInputField
import com.example.customerapp.ui.theme.components.GradientButton

@Composable
fun RegistrationScreen(onNavigateSuccessScreen : (RegistrationResponse?) -> Unit, viewModel: RegistrationViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    var registartionDialog by remember { mutableStateOf(false) }
    var registerData by remember { mutableStateOf<RegistrationResponse?>(null) }

    RegistrationScreenUi(  state = state, onEvent = viewModel::onEvent)
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->

            when (event) {

                is RegistrationUiEvent.NavigateToSuccess -> {
                    registartionDialog= true
                    registerData= event.registrationResponse
                    onNavigateSuccessScreen(registerData)
                }

                is RegistrationUiEvent.ShowError -> {

                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if(registartionDialog){
        AlertDialog(
            onDismissRequest = {
                registartionDialog = false
            },
            title = {
                Text(text = "Registration")
            },
            text={
                Text(text = "Customer registerd successfully Email is:  ${state.email}.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        registartionDialog = false

                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

}


@Composable
fun RegistrationScreenUi(
    state: RegistrationUIState,
    onEvent: (RegistrationEvent) -> Unit
) {
    val keyBoardcontroller = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 400.dp)
                .padding(24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))



            Text(
                text = "Create your account",
                style = MaterialTheme.typography.headlineLarge
            )



            Spacer(modifier = Modifier.height(24.dp))

            // Custom Input Fields
            CustomInputField(
                label = "Full Name",
                value = state.fullName,
                error = state.fullNameError,
                onValueChange = { onEvent(RegistrationEvent.FullName(it)) }
            )

            CustomInputField(
                label = "Email",
                value = state.email,
                error = state.emailError,
                onValueChange = { onEvent(RegistrationEvent.Email(it)) }
            )

            CustomInputField(
                label = "Phone Number",
                value = state.phone,
                error = state.phoneError,
                onValueChange = { onEvent(RegistrationEvent.Phone(it)) },
                keyboardType = KeyboardType.Phone,
                maxLength = 10
            )


            CityDropDown(
                selectedCity = state.city,
                error = state.cityError,
                onCitySelected = {
                    onEvent(RegistrationEvent.City(it))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            LaunchedEffect(state.isLoading) {
                Log.d("RegistrationUI", "isLoading = ${state.isLoading}")
            }
            GradientButton(
                text = "Create Account",
                onClick = {
                    keyBoardcontroller ?.hide()
                    onEvent(RegistrationEvent.Submit)
                },
                isLoading = state.isLoading
            )

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropDown(
    selectedCity: String,
    error: String?,
    onCitySelected: (String) -> Unit
) {

    val cityList = listOf(
        "Dubai",
        "Abu Dhabi",
        "Sharjah",
        "Riyadh"
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = selectedCity,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("City")
                },
                isError = error != null,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                cityList.forEach { city ->

                    DropdownMenuItem(
                        text = {
                            Text(city)
                        },
                        onClick = {
                            onCitySelected(city)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreenUi(
        state = RegistrationUIState(

            isLoading = true
        ),
        onEvent = {}
    )
}
