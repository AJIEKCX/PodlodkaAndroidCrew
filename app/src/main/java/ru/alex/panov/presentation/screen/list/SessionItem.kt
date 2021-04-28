package ru.alex.panov.presentation.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.alex.panov.data.model.Session
import ru.alex.panov.presentation.theme.AppTheme
import ru.alex.panov.presentation.widget.CircleAvatar
import ru.alex.panov.presentation.widget.FavouriteIcon

@Composable
internal fun SessionItem(
    session: Session,
    favouriteIds: Set<String>,
    onFavouriteClicked: (Session) -> Unit,
    onSessionClicked: (String) -> Unit
) {
    Card(
        Modifier
            .padding(top = 16.dp)
            .clickable { onSessionClicked(session.id) },
        elevation = AppTheme.elevations.card
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 4.dp, top = 12.dp, bottom = 12.dp)
        ) {
            CircleAvatar(
                url = session.imageUrl,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterVertically),
            )
            Column(
                Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    session.speaker,
                    style = AppTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    session.timeInterval,
                    style = AppTheme.typography.h2,
                )
                Text(
                    session.description,
                    style = AppTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            FavouriteIcon(
                isFavourite = session.id in favouriteIds,
                onClick = { onFavouriteClicked(session) },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}