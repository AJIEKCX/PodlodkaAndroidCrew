package ru.alex.panov.presentation.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.alex.panov.R
import ru.alex.panov.presentation.theme.AppTheme

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onPositiveAction: () -> Unit
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.logout_dialog_title),
                style = AppTheme.typography.h6
            )
        },
        text = {
            Text(
                stringResource(R.string.logout_dialog_message),
                style = AppTheme.typography.subtitle1
            )
        },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = stringResource(R.string.logout_dialog_btn_cancel), color = Color.Gray)
                }
                TextButton(
                    onClick = onPositiveAction,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(text = stringResource(R.string.logout_dialog_btn_positive))
                }
            }
        },
        onDismissRequest = onDismiss
    )
}