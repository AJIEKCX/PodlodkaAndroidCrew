package ru.alex.panov.presentation.screen.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.alex.panov.R
import ru.alex.panov.data.model.Session
import ru.alex.panov.domain.SessionInteractor
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val application: Application,
    private val interactor: SessionInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(SessionsUiState.Default)
    val uiState: StateFlow<SessionsUiState> = _uiState

    private var sessions: List<Session> = emptyList()

    init {
        loadData()
    }

    fun onErrorMessageShowed() {
        _uiState.value = uiState.value.copy(errorMessage = "")
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
            val errorMessage =
                application.resources.getString(R.string.sessions_add_to_favourites_error)
            _uiState.value = uiState.value.copy(errorMessage = errorMessage)
        } else {
            _uiState.value = createUiState(newFavouriteIds, uiState.value.searchText)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            sessions = interactor.getSessions()
            _uiState.value = createUiState(favouriteIds = emptySet(), searchText = "")
        }
    }

    private fun createUiState(favouriteIds: Set<String>, searchText: String): SessionsUiState {
        return SessionsUiState(
            sessionGroups = interactor.filterSessions(sessions, searchText).groupBy { it.date },
            favouriteSessions = sessions.filter { it.id in favouriteIds },
            favouriteIds = favouriteIds,
            searchText = searchText,
            isLoading = false
        )
    }

    companion object {
        private const val MAX_FAVOURITES = 3
    }
}