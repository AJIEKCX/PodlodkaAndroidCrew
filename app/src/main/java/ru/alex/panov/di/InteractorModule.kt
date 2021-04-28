package ru.alex.panov.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alex.panov.data.repository.SessionRepository
import ru.alex.panov.domain.SessionInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {
    @Provides
    @Singleton
    fun provideSessionRepository(repository: SessionRepository): SessionInteractor {
        return SessionInteractor(repository)
    }
}