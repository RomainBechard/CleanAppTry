package com.romainbechard.instantsystemtestapp.di

import android.content.Context
import com.romainbechard.instantsystemtestapp.InstantSystemTestApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext appContext: Context
    ): InstantSystemTestApplication {
        return appContext as InstantSystemTestApplication
    }
}