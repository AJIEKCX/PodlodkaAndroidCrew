package ru.alex.panov.presentation.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.alex.panov.data.model.Session
import ru.alex.panov.data.repository.SessionRepository
import ru.alex.panov.domain.SessionInteractor
import ru.alex.panov.presentation.MainDestinations
import javax.inject.Inject

@HiltViewModel
class SessionDetailsViewModel @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _session = MutableStateFlow<Session?>(null)
    val session: StateFlow<Session?> = _session

    init {
        val sessionId = savedStateHandle.get<String>(MainDestinations.SESSION_DETAILS_ID_KEY)!!
        loadData(sessionId)
    }

    private fun loadData(sessionId: String) {
        viewModelScope.launch {
            _session.value = sessionInteractor.getSession(sessionId)
        }
    }
}