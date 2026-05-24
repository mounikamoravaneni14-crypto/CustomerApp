package com.example.customerapp.ui.theme.registration

//button click will cal
sealed class RegistrationEvent {
    data class FullName(val value: String) : RegistrationEvent()
    data class Email(val value: String) : RegistrationEvent()
    data class Phone(val value: String) : RegistrationEvent()
    data class City(val value: String) : RegistrationEvent()

    object Submit : RegistrationEvent()
}