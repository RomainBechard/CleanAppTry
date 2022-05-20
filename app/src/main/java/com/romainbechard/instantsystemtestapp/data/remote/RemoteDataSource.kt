package com.romainbechard.instantsystemtestapp.data.remote

import com.romainbechard.instantsystemtestapp.BuildConfig
import com.romainbechard.instantsystemtestapp.data.NewsApi
import com.romainbechard.instantsystemtestapp.data.NewsDataSource
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.model.dto.toArticle
import com.romainbechard.instantsystemtestapp.data.tools.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RemoteDataSource(
    private val api: NewsApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NewsDataSource {

    override suspend fun getHeadlines(): Result<List<Article>> = withContext(dispatcher) {
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

    override suspend fun getSearchResult(subject: String): Result<List<Article>>  = withContext(dispatcher) {
        return@withContext try {
            val list = mutableListOf<Article>()
            val response = api.getSearchResult(subject = subject, apiKey = BuildConfig.NEWS_API_KEY)
            response.articles?.forEach{
                list.add(it.toArticle())
            }
            Result.Success(list)
        } catch (e: HttpException) {
            Result.Error(e)
        }
    }

}