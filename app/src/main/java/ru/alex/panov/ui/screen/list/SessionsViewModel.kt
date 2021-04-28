package ru.alex.panov.ui.screen.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.alex.panov.model.MockSessions

class SessionsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SessionsUiState.Default)
    val uiState: StateFlow<SessionsUiState> = _uiState

    private val sessions = MockSessions

    init {
        _uiState.value = createUiState(favouriteIds = emptySet(), searchText = "")
    }

    fun onErrorMessageShowed() {
        _uiState.value = uiState.value.copy(showErrorMessage = false)
    }

    fun onSearchTextChanged(value: String) {
        _uiState.value = createUiState(uiState.value.favouriteIds, value)
    }

    fun onFavouriteClicked(sessionId: String) {
        val newFavouriteIds = uiState.value.favouriteIds.toMutableSet().apply {
            val isSessionAdded = add(sessionId)
            if (isSessionAdded.not()) {
                remove(sessionId)
            }
        }
        if (newFavouriteIds.count() > MAX_FAVOURITES) {
            _uiState.value = uiState.value.copy(showErrorMessage = true)
        } else {
            _uiState.value = createUiState(newFavouriteIds, uiState.value.searchText)
        }
    }

    private fun createUiState(favouriteIds: Set<String>, searchText: String): SessionsUiState {
        return SessionsUiState(
            sessionGroups = sessions
                .filter { session ->
                    searchText.isBlank() ||
                            session.description.contains(searchText, ignoreCase = true) ||
                            session.speaker.contains(searchText, ignoreCase = true)
                }
                .groupBy { it.date },
            favouriteSessions = sessions.filter { it.id in favouriteIds },
            favouriteIds = favouriteIds,
            searchText = searchText
        )
    }

    companion object {
        private const val MAX_FAVOURITES = 3
    }
}