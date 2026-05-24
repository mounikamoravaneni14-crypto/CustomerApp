package com.example.customerapp.data.repository

import com.example.customerapp.data.repository.remote.ApiServices
import com.example.customerapp.model.RegistrationRequest
import com.example.customerapp.model.RegistrationResponse
import com.example.customerapp.util.BaseRepository
import com.wingspan.companyhub.util.ApiResult
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val apiServices: ApiServices): BaseRepository(){

    suspend fun registration (request: RegistrationRequest) : ApiResult<RegistrationResponse> =
        safeApiCall {
                apiServices.registration(request)
        }
}