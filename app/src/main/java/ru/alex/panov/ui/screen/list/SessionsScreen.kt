package ru.alex.panov.ui.screen.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.systemBarsPadding
import ru.alex.panov.R
import ru.alex.panov.model.Session
import ru.alex.panov.ui.theme.AppTheme
import ru.alex.panov.ui.widget.AppTextField

@Composable
fun SessionsScreen(
    sessionClicked: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: SessionsViewModel = viewModel()
) {
    var showLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
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

    if (uiState.showErrorMessage) {
        val errorMessage = stringResource(R.string.sessions_add_to_favourites_error)
        LaunchedEffect(scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(errorMessage)
            viewModel.onErrorMessageShowed()
        }
    }

    SessionsContent(
        Modifier.systemBarsPadding(),
        scaffoldState,
        uiState.sessionGroups,
        uiState.favourites,
        searchText,
        onSearchChanged = { searchText = it },
        onFavouriteClicked = { viewModel.onFavouriteClicked(it.id) },
        onSessionClicked = sessionClicked
    )
}

@Composable
private fun SessionsContent(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    sessionGroups: Map<String, List<Session>>,
    favourites: List<Session>,
    searchText: String,
    onSearchChanged: (String) -> Unit,
    onFavouriteClicked: (Session) -> Unit,
    onSessionClicked: (String) -> Unit
) {
    Scaffold(modifier, scaffoldState = scaffoldState, topBar = {
        SearchTextField(searchText, onSearchChanged)
    }) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            if (favourites.isNotEmpty()) {
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
                        items(favourites) { item ->
                            FavouriteSession(item, onSessionClicked)
                        }
                    }
                }
            }
            item {
                Text(
                    stringResource(R.string.sessions_title),
                    Modifier.paddingFromBaseline(top = 32.dp),
                    style = AppTheme.typography.h1
                )
            }
            sessionGroups.forEach { (date, sessions) ->
                item {
                    Text(
                        date,
                        Modifier.paddingFromBaseline(top = 32.dp),
                        style = AppTheme.typography.subtitle1
                    )
                }
                items(sessions) { item ->
                    SessionItem(item, onFavouriteClicked, onSessionClicked)
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    searchText: String,
    onSearchChanged: (String) -> Unit,
) {
    AppTextField(
        label = { Text(stringResource(R.string.sessions_search_label)) },
        value = searchText,
        onValueChange = onSearchChanged,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}