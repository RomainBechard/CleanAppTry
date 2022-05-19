package com.romainbechard.instantsystemtestapp.data.model

data class Article(
    val title: String,
    val description: String,
    val imageUrl: String,
    val articleUrl: String,
    val isExpanded: Boolean = false
)