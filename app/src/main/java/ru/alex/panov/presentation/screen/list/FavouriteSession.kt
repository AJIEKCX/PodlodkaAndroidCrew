package ru.alex.panov.presentation.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.alex.panov.data.model.Session
import ru.alex.panov.presentation.theme.AppTheme

@Composable
internal fun FavouriteSession(session: Session, onSessionClicked: (String) -> Unit) {
    Card(
        Modifier
            .size(136.dp)
            .clickable { onSessionClicked(session.id) },
        shape = AppTheme.shapes.medium,
        elevation = AppTheme.elevations.card
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                session.timeInterval,
                style = AppTheme.typography.h1,
            )
            Text(
                session.date,
                style = AppTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                session.speaker,
                style = AppTheme.typography.h2,
                modifier = Modifier.paddingFromBaseline(top = 24.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                session.description,
                style = AppTheme.typography.body1,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}