package com.romainbechard.instantsystemtestapp.data

import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.tools.Result

interface NewsDataSource {

    suspend fun getHeadlines(): Result<List<Article>>

    suspend fun getSearchResult(subject: String): Result<List<Article>>
}