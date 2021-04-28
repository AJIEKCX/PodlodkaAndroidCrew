package ru.alex.panov.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import ru.alex.panov.presentation.MainDestinations.SESSIONS_ROUTE
import ru.alex.panov.presentation.MainDestinations.SESSION_DETAILS_ID_KEY
import ru.alex.panov.presentation.MainDestinations.SESSION_DETAILS_ROUTE
import ru.alex.panov.presentation.screen.details.SessionDetailsScreen
import ru.alex.panov.presentation.screen.details.SessionDetailsViewModel
import ru.alex.panov.presentation.screen.list.SessionsScreen
import ru.alex.panov.presentation.screen.list.SessionsViewModel

object MainDestinations {
    const val SESSIONS_ROUTE = "sessions"
    const val SESSION_DETAILS_ROUTE = "session_details/{sessionId}"
    const val SESSION_DETAILS_ID_KEY = "sessionId"
}

@Composable
fun NavGraph(startDestination: String = SESSIONS_ROUTE, onFinish: () -> Unit) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController, onFinish) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(SESSIONS_ROUTE) {
            val viewModel = hiltNavGraphViewModel<SessionsViewModel>()
            SessionsScreen(actions.sessionClicked, actions.onBack, viewModel)
        }
        composable(
            "$SESSION_DETAILS_ROUTE/{$SESSION_DETAILS_ID_KEY}",
            arguments = listOf(navArgument(SESSION_DETAILS_ID_KEY) { type = NavType.StringType })
        ) {
            val viewModel = hiltNavGraphViewModel<SessionDetailsViewModel>()
            SessionDetailsScreen(viewModel)
        }
    }
}

class MainActions(navController: NavHostController, onFinish: () -> Unit) {
    val sessionClicked: (String) -> Unit = { sessionId ->
        navController.navigate("$SESSION_DETAILS_ROUTE/$sessionId")
    }

    val onBack: () -> Unit = {
        val isPopped = navController.popBackStack()
        if (isPopped.not()) {
            onFinish()
        }
    }
}
