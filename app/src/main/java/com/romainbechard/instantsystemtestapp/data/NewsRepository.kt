package com.romainbechard.instantsystemtestapp.data

import com.romainbechard.instantsystemtestapp.BuildConfig
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.model.dto.toArticle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.romainbechard.instantsystemtestapp.data.tools.Result
import retrofit2.HttpException

class NewsRepository(
    private val api: NewsApi,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getHeadlines(): Result<List<Article>> = withContext(dispatcher) {
        return@withContext try {
            val list = mutableListOf<Article>()
            val response = api.getHeadlines(country = "fr", apiKey = BuildConfig.NEWS_API_KEY)
            response.articles?.forEach{
                list.add(it.toArticle())
            }
            Result.Success(list)
        } catch (e: HttpException) {
            Result.Error(e)
        }
    }
}