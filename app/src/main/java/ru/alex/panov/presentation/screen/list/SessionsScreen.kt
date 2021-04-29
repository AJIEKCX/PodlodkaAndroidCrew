package ru.alex.panov.presentation.screen.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.systemBarsPadding
import ru.alex.panov.R
import ru.alex.panov.data.model.Session
import ru.alex.panov.presentation.theme.AppTheme
import ru.alex.panov.presentation.widget.AppProgress
import ru.alex.panov.presentation.widget.SearchTextField

@Composable
fun SessionsScreen(
    sessionClicked: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: SessionsViewModel = viewModel()
) {
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val uiState by viewModel.uiState.collectAsState()

    BackHandler { showLogoutDialog = true }

    if (showLogoutDialog) {
        val onDismiss = { showLogoutDialog = false }
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onPositiveAction = { onDismiss(); onBack() }
        )
    }

    if (uiState.errorMessage.isNotEmpty()) {
        LaunchedEffect(scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(uiState.errorMessage)
            viewModel.onErrorMessageShowed()
        }
    }

    if (uiState.isLoading) {
        AppProgress()
    } else {
        SessionsContent(
            uiState,
            scaffoldState,
            Modifier.systemBarsPadding(),
            onSearchChanged = viewModel::onSearchTextChanged,
            onFavouriteClicked = { viewModel.onFavouriteClicked(it.id) },
            onSessionClicked = sessionClicked
        )
    }
}

@Composable
private fun SessionsContent(
    uiState: SessionsUiState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onSearchChanged: (String) -> Unit,
    onFavouriteClicked: (Session) -> Unit,
    onSessionClicked: (String) -> Unit
) {
    Scaffold(modifier, scaffoldState = scaffoldState, topBar = {
        SearchTextField(uiState.searchText, onSearchChanged)
    }) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (uiState.favouriteSessions.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.sessions_favourites_title),
                        Modifier.paddingFromBaseline(top = 32.dp),
                        style = AppTheme.typography.h1
                    )
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.favouriteSessions, key = { it.id }) { item ->
                            FavouriteSession(item, onSessionClicked)
                        }
                    }
                }
            }
            if (uiState.sessionGroups.isNotEmpty()) {
                item {
                    Text(
                        stringResource(R.string.sessions_title),
                        Modifier.paddingFromBaseline(top = 32.dp),
                        style = AppTheme.typography.h1
                    )
                }
                uiState.sessionGroups.forEach { (date, sessions) ->
                    item {
                        Text(
                            date,
                            Modifier.paddingFromBaseline(top = 32.dp),
                            style = AppTheme.typography.subtitle1
                        )
                    }
                    items(sessions, key = { it.id }) { item ->
                        SessionItem(
                            item,
                            uiState.favouriteIds,
                            onFavouriteClicked,
                            onSessionClicked
                        )
                    }
                }
            }
        }
    }
}