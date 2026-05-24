package com.example.customerapp.util

import com.example.customerapp.model.ApiError
import com.google.gson.Gson
import com.wingspan.companyhub.util.ApiResult

import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {

    protected  suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> {

        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    ApiResult.Success(Unit as T)   // delete and update has no body only 200
                }

            } else {
                //able all error codes here
                val code = response.code()
                val errorBody = response.errorBody()?.string()

                println("HTTP Code: $code")
                println("Raw Error Body: $errorBody")

                val message = try {
                    val apiError = Gson().fromJson(errorBody, ApiError::class.java)
                    apiError?.error ?: apiError?.msg ?: "Something went wrong"
                } catch (e: Exception) {
                    println("JSON Parsing Failed: ${e.message}")
                    "Something went wrong"
                }

                when (code) {

                    in 400..499 -> {
                        // Client errors
                        ApiResult.Error(message)
                    }

                    in 500..599 -> {
                        // Server errors
                        ApiResult.Error("Server Error. Please try again later")
                    }

                    else -> {
                        ApiResult.Error(message)
                    }
                }
            }
        }
        //SocketTimeoutException is a child of IOException so first write this alter IOException
        catch (e: SocketTimeoutException) {
            ApiResult.Error("Server is taking too long to respond. Please try again.", e)
        }catch (e: IOException) {
            //IO exception means network failed.
            ApiResult.Error(
                "Network error. Please check your connection.",
                e
            )

        } catch (e: Exception) {

            ApiResult.Error(
                e.message ?: "Unexpected error occurred", e
            )
        }
    }
}