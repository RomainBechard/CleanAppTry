package com.romainbechard.instantsystemtestapp.di

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.romainbechard.instantsystemtestapp.data.NewsApi
import com.romainbechard.instantsystemtestapp.data.NewsDataSource
import com.romainbechard.instantsystemtestapp.data.NewsRepository
import com.romainbechard.instantsystemtestapp.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesApi(): NewsApi = Retrofit.Builder()
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

    @Singleton
    @AppModule.RemoteDataSource
    @Provides
    fun providesRemoteDataSource(): NewsDataSource {
        val api = providesApi()
        return RemoteDataSource(api)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        @AppModule.RemoteDataSource remoteTasksDataSource: NewsDataSource
    ): NewsRepository {
        return NewsRepository(
            remoteTasksDataSource
        )
    }
}