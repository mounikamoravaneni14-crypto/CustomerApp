# CustomerApp

A simple Android application built using Kotlin and MVVM architecture.  
The app captures customer details, validates input, sends data to a mock REST API, and shows a success screen on successful submission.

## 🚀 Features

- Customer Form with validation
- Real-time input validation
- Dropdown city selection (hardcoded values)
- API integration using Retrofit
- Loading indicator during API call
- Success screen with submitted data
- Error handling with Toast

## 🏗️ Tech Stack

- Kotlin
- MVVM Architecture
- Jetpack Compose 
- Retrofit 2
- Coroutines (viewModelScope)
- StateFlow / LiveData
- Hilt (Dependency Injection)
- Jetpack Navigation Component

## 📡 Mock API Details

- Tool: MockAPI.io  
- Endpoint: https://6a11acc33e35d0f37ee38555.mockapi.io/api/v1/customers

https://6a11acc33e35d0f37ee38555.mockapi.io/api/v1/customers




📱 App Flow:
Screen 1: Customer Form
User enters:
Full Name
Email
Phone Number
City (Dropdown)
Validation happens in real time
Submit enabled only when all fields are valid
On submit → API call
Screen 2: Success Screen
Displayed after HTTP 200/201 response
Shows confirmation message
Displays submitted customer data
🧠 Architecture (MVVM)
UI Layer → Compose Screens
ViewModel → Handles UI state, validation, API triggers
Repository → Handles API calls
Network Layer → Retrofit service

This architecture is used to:

Separate concerns
Improve testability
Keep code clean and scalable

Screenshots :
<img width="1080" height="2400" alt="registerscreen" src="https://github.com/user-attachments/assets/54b1d4bc-29ba-4fdb-b980-62e24aa4951c" />

<img width="1080" height="2400" alt="successscreen" src="https://github.com/user-attachments/assets/eb932c32-1fa7-4e3e-ba78-6221ef44245a" />

⚠️ Assumptions
All fields are mandatory
City must be selected from dropdown only
API returns HTTP 201 on success
No local database used

