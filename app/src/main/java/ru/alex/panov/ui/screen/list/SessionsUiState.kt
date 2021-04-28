package ru.alex.panov.ui.screen.list

import ru.alex.panov.model.Session

data class SessionsUiState(
    val sessionGroups: Map<String, List<Session>>,
    val favouriteSessions: List<Session>,
    val favouriteIds: Set<String>,
    val searchText: String,
    val showErrorMessage: Boolean = false
) {
    companion object {
        val Default = SessionsUiState(
            sessionGroups = emptyMap(),
            favouriteSessions = emptyList(),
            favouriteIds = emptySet(),
            searchText = "",
            showErrorMessage = false
        )
    }
}
