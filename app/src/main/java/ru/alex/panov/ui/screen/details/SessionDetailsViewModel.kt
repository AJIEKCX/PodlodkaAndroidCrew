package ru.alex.panov.ui.screen.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.alex.panov.model.MockSessions
import ru.alex.panov.model.Session
import ru.alex.panov.ui.MainDestinations

class SessionDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _session = MutableStateFlow<Session?>(null)
    val session: StateFlow<Session?> = _session

    init {
        val sessionId = savedStateHandle.get<String>(MainDestinations.SESSION_DETAILS_ID_KEY)
        _session.value = MockSessions.single { it.id == sessionId }
    }
}