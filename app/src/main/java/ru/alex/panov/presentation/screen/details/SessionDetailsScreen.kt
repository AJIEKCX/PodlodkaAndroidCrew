package ru.alex.panov.presentation.screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.alex.panov.data.model.Session
import ru.alex.panov.presentation.theme.AppTheme
import ru.alex.panov.presentation.widget.CircleAvatar

@Composable
fun SessionDetailsScreen(viewModel: SessionDetailsViewModel = viewModel()) {
    val session by viewModel.session.collectAsState()
    session?.let {
        SessionDetailsContent(it)
    }
}

@Composable
private fun SessionDetailsContent(session: Session) {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            CircleAvatar(
                url = session.imageUrl,
                modifier = Modifier
                    .size(256.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                session.speaker,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = AppTheme.typography.h1,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(24.dp))
            Row {
                Icon(Icons.Default.CalendarToday, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(
                    "${session.date}, ${session.timeInterval}",
                    style = AppTheme.typography.body1,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                session.description,
                style = AppTheme.typography.body1,
                fontSize = 18.sp
            )
        }
    }
}