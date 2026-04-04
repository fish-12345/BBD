package io.github.romanvht.byedpi.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import io.github.romanvht.byedpi.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.romanvht.byedpi.data.DomainList
import io.github.romanvht.byedpi.ui.viewmodel.DomainListsViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomainListsScreen(
    onBack: () -> Unit,
    viewModel: DomainListsViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.domain_lists)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddDialogAction() }
            ) {
                Icon(Icons.Default.Add, "Добавить список")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.domainLists, key = { it.id }) { domainList ->
                DomainListItem(
                    domainList = domainList,
                    onToggle = { viewModel.toggleListActive(it) },
                    onClick = { viewModel.showActionDialogAction(it) }
                )
            }
        }

        // Add Dialog
        if (viewModel.showAddDialog) {
            DomainListEditDialog(
                title = "Добавить список",
                initialName = "",
                initialDomains = "",
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { name, domains ->
                    val domainList = domains
                        .lines()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                    // Теперь просто вызываем функцию, аргументы onSuccess/onError не нужны
                    viewModel.addList(name = name, domains = domainList)
                }
            )
        }

        // Edit Dialog
        if (viewModel.showEditDialog && viewModel.editingList != null) {
            val list = viewModel.editingList!!
            DomainListEditDialog(
                title = "Редактировать список",
                initialName = list.name,
                initialDomains = list.domains.joinToString("\n"),
                onDismiss = { viewModel.hideEditDialog() },
                onConfirm = { name, domains ->
                    val domainList = domains
                        .lines()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                    viewModel.updateList(id = list.id, name = name, domains = domainList)
                }
            )
        }

        // Action Dialog
        if (viewModel.showActionDialog && viewModel.selectedList != null) {
            val list = viewModel.selectedList!!

            AlertDialog(
                onDismissRequest = { viewModel.hideActionDialog() },
                title = { Text(list.name) },
                text = {
                    Column {

                        ListItem(
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            headlineContent = { Text("Редактировать") },
                            leadingContent = { Icon(Icons.Default.Edit, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.hideActionDialog()
                                    viewModel.showEditDialogAction(list)
                                }
                        )

                        ListItem(
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            headlineContent = { Text("Копировать") },
                            leadingContent = { Icon(Icons.Default.ContentCopy, contentDescription = null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val clipboardManager =
                                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText(
                                        list.name,
                                        viewModel.copyDomainsToClipboard(list)
                                    )
                                    clipboardManager.setPrimaryClip(clip)

                                    viewModel.hideActionDialog()
                                    Toast.makeText(context, "Скопировано", Toast.LENGTH_SHORT).show()
                                }
                        )

                        if (!list.isBuiltIn) {
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                DividerDefaults.color
                            )

                            ListItem(
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                headlineContent = { Text("Удалить", color = MaterialTheme.colorScheme.error) },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.deleteList(list.id)
                                        viewModel.hideActionDialog()
                                        Toast.makeText(context, "Список удален", Toast.LENGTH_SHORT).show()
                                    }
                            )
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { viewModel.hideActionDialog() }) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

@Composable
fun DomainListItem(
    domainList: DomainList,
    onToggle: (DomainList) -> Unit,
    onClick: (DomainList) -> Unit
) {
    Card(
        onClick = { onClick(domainList) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = domainList.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                val summary = domainList.domains.take(5).joinToString("\n")
                val displayText = if (domainList.domains.size > 5) {
                    "$summary\n..."
                } else {
                    summary
                }

                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Checkbox(
                checked = domainList.isActive,
                onCheckedChange = { onToggle(domainList) }
            )
        }
    }
}

@Composable
fun DomainListEditDialog(
    title: String,
    initialName: String,
    initialDomains: String,
    onDismiss: () -> Unit,
    onConfirm: (name: String, domains: String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var domains by remember { mutableStateOf(initialDomains) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = domains,
                    onValueChange = { domains = it },
                    label = { Text("Домены (1 на линию)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, domains) }
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}