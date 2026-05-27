package com.example.kpmapp_with_di.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kpmapp_with_di.data.about.AboutRepository
import com.example.kpmapp_with_di.ui.about.RowView
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AboutScreen(
    onUpButtonClick: () -> Unit
) {
    val viewModel: AboutViewModel = koinViewModel()

    Column {
        Toolbar(onUpButtonClick)
        AboutContent(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onUpButtonClick: () -> Unit) {
    TopAppBar(
        title = { Text("About Device") },
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
private fun AboutContent(viewModel: AboutViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn {
        items(state.platformInfo) { row ->
            RowView(row.first, row.second)
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Visited ${state.visitedCount} times.")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Last visited at ${state.visitedDate}.")
            }
        }
    }
}

@Composable
private fun RowView(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            subtitle,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}