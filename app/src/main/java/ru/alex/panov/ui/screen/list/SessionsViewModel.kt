package ru.alex.panov.ui.screen.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.alex.panov.model.MockSessions
import ru.alex.panov.model.Session

class SessionsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SessionsUiState.Default)
    val uiState: StateFlow<SessionsUiState> = _uiState

    init {
        _uiState.value = createUiState(MockSessions)
    }

    fun onErrorMessageShowed() {
        _uiState.value = uiState.value.copy(showErrorMessage = false)
    }

    fun onFavouriteClicked(sessionId: String) {
        val newSessions = uiState.value.sessionGroups.flatMap { it.value }
            .map { session ->
                if (session.id == sessionId) {
                    session.copy(isFavourite = session.isFavourite.not())
                } else {
                    session
                }
            }
        if (newSessions.count { it.isFavourite } > MAX_FAVOURITES) {
            _uiState.value = uiState.value.copy(showErrorMessage = true)
        } else {
            _uiState.value = createUiState(newSessions)
        }
    }

    private fun createUiState(sessions: List<Session>): SessionsUiState {
        return SessionsUiState(
            sessionGroups = sessions.groupBy { it.date },
            favourites = sessions.filter { it.isFavourite }
        )
    }

    companion object {
        private const val MAX_FAVOURITES = 3
    }
}