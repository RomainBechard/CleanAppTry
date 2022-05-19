package com.romainbechard.instantsystemtestapp

import android.app.Application
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.romainbechard.instantsystemtestapp.data.NewsApi
import com.romainbechard.instantsystemtestapp.data.NewsRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class InstantSystemTestApplication : Application() {

    private val newsApi: NewsApi = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(
            JacksonConverterFactory.create(
                ObjectMapper()
                    .registerKotlinModule()
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            )
        )
        .client(
            OkHttpClient().newBuilder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        )
        .build()
        .create(NewsApi::class.java)

    lateinit var repository: NewsRepository


    override fun onCreate() {
        super.onCreate()
        repository = NewsRepository(
            api = newsApi,
            dispatcher = Dispatchers.IO
        )
    }
}


