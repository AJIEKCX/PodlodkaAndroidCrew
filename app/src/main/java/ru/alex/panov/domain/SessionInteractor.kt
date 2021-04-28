package ru.alex.panov.domain

import ru.alex.panov.data.model.Session
import ru.alex.panov.data.repository.SessionRepository
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val sessionRepository: SessionRepository
) {
    suspend fun getSessions(): List<Session> {
        return sessionRepository.getSessions()
    }

    suspend fun getSession(id: String): Session {
        return sessionRepository.getSession(id)
    }

    fun filterSessions(sessions: List<Session>, searchText: String): List<Session> {
        return sessions.filter { session ->
            searchText.isBlank() ||
                    session.description.contains(searchText, ignoreCase = true) ||
                    session.speaker.contains(searchText, ignoreCase = true)
        }
    }
}