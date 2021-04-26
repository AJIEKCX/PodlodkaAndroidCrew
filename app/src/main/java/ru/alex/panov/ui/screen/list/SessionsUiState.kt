package ru.alex.panov.ui.screen.list

import ru.alex.panov.model.Session

data class SessionsUiState(
    val sessionGroups: Map<String, List<Session>>,
    val favourites: List<Session>,
    val showErrorMessage: Boolean = false
) {
    companion object {
        val Default = SessionsUiState(
            sessionGroups = emptyMap(),
            favourites = emptyList(),
            showErrorMessage = false
        )
    }
}
