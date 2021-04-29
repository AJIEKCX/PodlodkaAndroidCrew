package ru.alex.panov.presentation.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.alex.panov.R

@Composable
fun SearchTextField(
    searchText: String,
    onSearchChanged: (String) -> Unit,
    onDone: () -> Unit
) {
    AppTextField(
        label = { Text(stringResource(R.string.sessions_search_label)) },
        value = searchText,
        onValueChange = onSearchChanged,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchChanged("") }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        },
        modifier = Modifier.padding(horizontal = 16.dp),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    )
}