package ru.alex.panov.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.alex.panov.data.model.Session
import ru.alex.panov.domain.SessionInteractor
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val interactor: SessionInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(SessionsUiState.Default)
    val uiState: StateFlow<SessionsUiState> = _uiState

    private var sessions: List<Session> = emptyList()

    init {
        loadData()
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

    private fun loadData() {
        viewModelScope.launch {
            sessions = interactor.getSessions()
            _uiState.value = createUiState(favouriteIds = emptySet(), searchText = "")
        }
    }

    private fun createUiState(favouriteIds: Set<String>, searchText: String): SessionsUiState {
        return SessionsUiState(
            sessionGroups = interactor.filterSessions(sessions, searchText).groupBy { it.date },
            favouriteSessions = sessions.filter { it.id in favouriteIds },
            favouriteIds = favouriteIds,
            searchText = searchText
        )
    }

    companion object {
        private const val MAX_FAVOURITES = 3
    }
}