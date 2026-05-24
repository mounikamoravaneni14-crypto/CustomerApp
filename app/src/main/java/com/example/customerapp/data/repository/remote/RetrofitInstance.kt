package com.example.customerapp.data.repository.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    val BASE_URL="https://6a11acc33e35d0f37ee38555.mockapi.io/api/v1/"
    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun retrofitProvider(okhttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okhttp).baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiServices(retrofit : Retrofit): ApiServices =
    retrofit.create(ApiServices::class.java)
}