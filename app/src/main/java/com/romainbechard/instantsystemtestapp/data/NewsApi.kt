package com.romainbechard.instantsystemtestapp.data

import com.romainbechard.instantsystemtestapp.data.model.dto.GetHeadlinesResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): GetHeadlinesResponseDTO

    @GET("v2/everything")
    suspend fun getSearchResult(
        @Query("apiKey") apiKey: String,
        @Query("q") subject: String,
        @Query("sortBy") sortBy: String = "popularity"
    ): GetHeadlinesResponseDTO
}