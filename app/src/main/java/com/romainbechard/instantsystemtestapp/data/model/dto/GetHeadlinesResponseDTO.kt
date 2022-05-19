package com.romainbechard.instantsystemtestapp.data.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetHeadlinesResponseDTO(
    @JsonProperty("status") val status: String?,
    @JsonProperty("totalResults") val totalResultNumber: Int?,
    @JsonProperty("articles") val articles: List<ArticleDTO>?
)
