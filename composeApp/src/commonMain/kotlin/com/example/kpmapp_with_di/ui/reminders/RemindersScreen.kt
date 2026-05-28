package com.example.kpmapp_with_di.ui.reminders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.example.kpmapp_with_di.data.reminders.Reminder
import org.koin.compose.viewmodel.koinViewModel

private val logger = Logger.withTag("RemindersScreen")

@Composable
internal fun RemindersScreen(
    onUpButtonClick: () -> Unit
) {
    val viewModel: RemindersViewModel = koinViewModel()
    val reminders by viewModel.state.collectAsStateWithLifecycle()
    var textFieldValue by remember { mutableStateOf("") }

    logger.d { "Recomposing with ${reminders.size} reminders" }

    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(onUpButtonClick = onUpButtonClick)
        RemindersContent(
            reminders = reminders,
            textFieldValue = textFieldValue,
            onValueChange = { textFieldValue = it },
            onSubmit = {
                viewModel.createReminder(textFieldValue)
                textFieldValue = ""
            },
            onItemClick = { reminder ->
                viewModel.markReminder(reminder.id, !reminder.isCompleted)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onUpButtonClick: () -> Unit) {
    TopAppBar(
        title = { Text("Reminders") },
        navigationIcon = {
            IconButton(onClick = onUpButtonClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
private fun RemindersContent(
    reminders: List<Reminder>,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onItemClick: (Reminder) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = reminders, key = { it.id }) { reminder ->
            ReminderItem(
                title = reminder.title,
                isCompleted = reminder.isCompleted,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(reminder) }
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
        item {
            NewReminderTextField(
                value = textFieldValue,
                onValueChange = onValueChange,
                onSubmit = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun ReminderItem(
    title: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = null
        )
        Text(
            text = title,
            style = if (isCompleted) {
                MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.LineThrough,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            } else {
                MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun NewReminderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Add a new reminder here") },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSubmit() }
        ),
        modifier = modifier.onPreviewKeyEvent { event: KeyEvent ->
            if (event.key == Key.Enter) {
                onSubmit()
                true
            } else false
        }
    )
}