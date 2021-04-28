package ru.alex.panov.presentation.screen.list

import ru.alex.panov.data.model.Session

data class SessionsUiState(
    val sessionGroups: Map<String, List<Session>>,
    val favouriteSessions: List<Session>,
    val favouriteIds: Set<String>,
    val searchText: String,
    val errorMessage: String = "",
    val isLoading: Boolean = false
) {
    companion object {
        val Default = SessionsUiState(
            sessionGroups = emptyMap(),
            favouriteSessions = emptyList(),
            favouriteIds = emptySet(),
            searchText = "",
            errorMessage = "",
            isLoading = false
        )
    }
}
