package com.romainbechard.instantsystemtestapp

import com.romainbechard.instantsystemtestapp.data.Repository
import com.romainbechard.instantsystemtestapp.data.model.Article
import com.romainbechard.instantsystemtestapp.data.tools.Result
import java.lang.Exception

class FakeRepository() : Repository {

    override suspend fun getHeadlines(): Result<List<Article>> {
        return Result.Success(
            listOf(
                Article(
                    title = "hello",
                    description = "world",
                    imageUrl = "",
                    articleUrl = "",
                    isExpanded = false
                )
            )
        )
    }

    override suspend fun getSearchResult(subject: String): Result<List<Article>> {
        return Result.Success(
            listOf(
                Article(
                    title = "hello",
                    description = "world",
                    imageUrl = "",
                    articleUrl = "",
                    isExpanded = false
                )
            )
        )
    }

    override fun getSubjects(): List<String> =
        listOf("Sport", "People", "Politique", "Santé", "Divers")
}