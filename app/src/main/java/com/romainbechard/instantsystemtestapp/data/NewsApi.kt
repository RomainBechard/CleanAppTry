package com.romainbechard.instantsystemtestapp.data

import com.romainbechard.instantsystemtestapp.data.model.dto.GetHeadlinesResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): GetHeadlinesResponseDTO
}