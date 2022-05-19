package com.romainbechard.instantsystemtestapp.data.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.romainbechard.instantsystemtestapp.data.model.Article

data class ArticleDTO(
    @JsonProperty("source") val source: SourceDTO?,
    @JsonProperty("author") val author: String?,
    @JsonProperty("title") val title: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("url") val url: String?,
    @JsonProperty("urlToImage") val urlToImage: String?,
    @JsonProperty("publishedAt") val publishDate: String?,
    @JsonProperty("content") val content: String?
)

fun ArticleDTO.toArticle() =
    Article(
        title = this.title ?: "",
        description = this.description ?: "",
        imageUrl = this.urlToImage ?: "",
        articleUrl = this.url ?: ""
    )
