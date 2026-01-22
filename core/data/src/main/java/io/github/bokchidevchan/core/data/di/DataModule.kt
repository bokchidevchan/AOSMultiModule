package io.github.bokchidevchan.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.bokchidevchan.core.data.repository.ArticleRepositoryImpl
import io.github.bokchidevchan.core.data.repository.SearchRepositoryImpl
import io.github.bokchidevchan.core.data.repository.SettingsRepositoryImpl
import io.github.bokchidevchan.core.data.repository.UserRepositoryImpl
import io.github.bokchidevchan.core.domain.repository.ArticleRepository
import io.github.bokchidevchan.core.domain.repository.SearchRepository
import io.github.bokchidevchan.core.domain.repository.SettingsRepository
import io.github.bokchidevchan.core.domain.repository.UserRepository
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindArticleRepository(impl: ArticleRepositoryImpl): ArticleRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
