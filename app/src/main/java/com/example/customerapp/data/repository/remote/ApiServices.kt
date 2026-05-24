package com.example.customerapp.data.repository.remote

import com.example.customerapp.model.RegistrationRequest
import com.example.customerapp.model.RegistrationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //registration
    @POST("customers")
    suspend fun registration(@Body request : RegistrationRequest): Response<RegistrationResponse>


}