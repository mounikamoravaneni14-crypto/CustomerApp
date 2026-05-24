package com.example.customerapp.ui.theme.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customerapp.data.repository.RegistrationRepository
import com.example.customerapp.model.RegistrationRequest
import com.example.customerapp.model.RegistrationResponse
import com.example.customerapp.model.RegistrationUIState
import com.wingspan.companyhub.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationViewModel @Inject constructor(val repository: RegistrationRepository): ViewModel() {
    private val _state = MutableStateFlow(RegistrationUIState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegistrationUiEvent>()
    val event = _event.asSharedFlow()


    fun onEvent(event: RegistrationEvent) {
        when (event) {

            is RegistrationEvent.FullName -> {
                _state.value = _state.value.copy(fullName = event.value)
            }

            is RegistrationEvent.Email -> {
                _state.value = _state.value.copy(email = event.value)
            }

            is RegistrationEvent.Phone -> {
                _state.value = _state.value.copy(phone = event.value)
            }

            is RegistrationEvent.City -> {
                _state.value = _state.value.copy(city = event.value)
            }



            RegistrationEvent.Submit -> {
               register()
            }
        }
    }

    fun registerValidation(): Boolean {

        val current = _state.value

        var updatedState = current.copy(
            fullNameError = null,
            emailError = null,
            phoneError = null,
            cityError = null
        )

        var valid = true

        // Full Name Validation
        when {

            current.fullName.isBlank() -> {
                updatedState =
                    updatedState.copy(
                        fullNameError = "Full Name is required"
                    )
                valid = false
            }

            current.fullName.length < 2 -> {
                updatedState =
                    updatedState.copy(
                        fullNameError = "Minimum 2 characters required"
                    )
                valid = false
            }

            !current.fullName.matches(
                Regex("^[a-zA-Z ]+$")
            ) -> {

                updatedState =
                    updatedState.copy(
                        fullNameError =
                            "Only alphabets allowed"
                    )

                valid = false
            }
        }

        // Email Validation
        when {

            current.email.isBlank() -> {

                updatedState =
                    updatedState.copy(
                        emailError = "Email is required"
                    )

                valid = false
            }

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(current.email)
                .matches() -> {

                updatedState =
                    updatedState.copy(
                        emailError = "Invalid email format"
                    )

                valid = false
            }
        }

        // Phone Validation
        when {

            current.phone.isBlank() -> {

                updatedState =
                    updatedState.copy(
                        phoneError = "Phone number is required"
                    )

                valid = false
            }

            !current.phone.matches(
                Regex("^[0-9]+$")
            ) -> {

                updatedState =
                    updatedState.copy(
                        phoneError = "Digits only allowed"
                    )

                valid = false
            }

            current.phone.length < 7 -> {

                updatedState =
                    updatedState.copy(
                        phoneError =
                            "Minimum 7 digits required"
                    )

                valid = false
            }

            current.phone.length > 15 -> {

                updatedState =
                    updatedState.copy(
                        phoneError =
                            "Maximum 15 digits allowed"
                    )

                valid = false
            }
        }

        // City Validation
        if (current.city.isBlank()) {

            updatedState =
                updatedState.copy(
                    cityError = "Please select city"
                )

            valid = false
        }

        _state.value = updatedState

        return valid
    }
    fun register() {
        if (!registerValidation()) return

        viewModelScope.launch {

            // ✅ get current state once
            val current = _state.value

            // ✅ start loading
            _state.value = current.copy(isLoading = true)

            try {

                val result = repository.registration(
                    RegistrationRequest(
                        name = current.fullName,
                        email = current.email,
                        phone = current.phone,
                        city = current.city
                    )
                )

                when (result) {

                    is ApiResult.Success -> {
                        _state.value = _state.value.copy(isLoading = false)

                        _event.emit(
                            RegistrationUiEvent.NavigateToSuccess(
                                registrationResponse = RegistrationResponse(name=result.data.name,
                                    email= result.data.email,
                                    phone =result.data.phone,
                                    city = result.data.city)
                            )
                        )
                    }

                    is ApiResult.Error -> {
                        _state.value = _state.value.copy(isLoading = false)

                        _event.emit(
                            RegistrationUiEvent.ShowError(result.message)
                        )
                    }
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)

                _event.emit(
                    RegistrationUiEvent.ShowError(e.message ?: "Something went wrong")
                )
            }
        }
    }
}