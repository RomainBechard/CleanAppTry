package com.romainbechard.instantsystemtestapp

import com.romainbechard.instantsystemtestapp.data.NewsRepository
import org.mockito.Mockito.mock

fun mockRepository(): NewsRepository {
    return mock(NewsRepository::class.java)
}