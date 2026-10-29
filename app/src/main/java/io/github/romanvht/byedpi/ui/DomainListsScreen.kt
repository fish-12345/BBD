package io.github.romanvht.byedpi.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.DomainList
import io.github.romanvht.byedpi.data.icons.*
import io.github.romanvht.byedpi.ui.viewmodel.DomainListsViewModel
import io.github.romanvht.byedpi.utility.isTv
import kotlinx.coroutines.flow.collectLatest

/**
 * «Списки доменов» из настроек подбора стратегий.
 *
 * Активные списки подставляются в тест стратегий, встроенные наборы
 * читаются из assets и обновляются вместе с приложением.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomainListsScreen(
    onBack: () -> Unit,
    viewModel: DomainListsViewModel = viewModel()
) {
    val context = LocalContext.current
    val isTv = remember { context.isTv() }

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collectLatest { messageRes ->
            Toast.makeText(context, context.getString(messageRes), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.domain_lists)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cancel)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.showAddDialogAction() },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.domain_list_add)) }
            )
        }
    ) { paddingValues ->
        if (viewModel.domainLists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Ico.Language,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.domain_list_empty),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.domain_list_empty_hint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    start = if (isTv) 48.dp else 16.dp,
                    end = if (isTv) 48.dp else 16.dp,
                    top = 16.dp,
                    bottom = 96.dp
                ),
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
        }
    }

    if (viewModel.showAddDialog) {
        DomainListEditDialog(
            title = stringResource(R.string.domain_list_add),
            initialName = "",
            initialDomains = "",
            onDismiss = { viewModel.hideAddDialog() },
            onConfirm = { name, domains ->
                viewModel.addList(name = name, domains = parseDomains(domains))
            }
        )
    }

    if (viewModel.showEditDialog && viewModel.editingList != null) {
        val list = viewModel.editingList!!
        DomainListEditDialog(
            title = stringResource(R.string.domain_list_edit),
            initialName = list.name,
            initialDomains = list.domains.joinToString("\n"),
            onDismiss = { viewModel.hideEditDialog() },
            onConfirm = { name, domains ->
                viewModel.updateList(id = list.id, name = name, domains = parseDomains(domains))
            }
        )
    }

    if (viewModel.showActionDialog && viewModel.selectedList != null) {
        val list = viewModel.selectedList!!

        if (isTv) {
            val copiedText = stringResource(R.string.domain_list_copied)
            AlertDialog(
                onDismissRequest = { viewModel.hideActionDialog() },
                title = { Text(list.name) },
                text = {
                    Column {
                        DomainListActionRow(
                            icon = Icons.Default.Edit,
                            label = stringResource(R.string.edit)
                        ) {
                            viewModel.hideActionDialog()
                            viewModel.showEditDialogAction(list)
                        }
                        DomainListActionRow(
                            icon = Ico.ContentCopy,
                            label = stringResource(R.string.domain_list_copy)
                        ) {
                            copyToClipboard(context, list.name, viewModel.copyDomainsToClipboard(list))
                            viewModel.hideActionDialog()
                            Toast.makeText(context, copiedText, Toast.LENGTH_SHORT).show()
                        }
                        if (!list.isBuiltIn) {
                            DomainListActionRow(
                                icon = Icons.Default.Delete,
                                label = stringResource(R.string.delete),
                                destructive = true
                            ) {
                                viewModel.deleteList(list.id)
                                viewModel.hideActionDialog()
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { viewModel.hideActionDialog() }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        } else {
            ModalBottomSheet(
                onDismissRequest = { viewModel.hideActionDialog() },
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                val copiedText = stringResource(R.string.domain_list_copied)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    Text(
                        text = list.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            top = 16.dp,
                            end = 24.dp,
                            bottom = 8.dp
                        )
                    )

                    DomainListActionRow(
                        icon = Icons.Default.Edit,
                        label = stringResource(R.string.edit)
                    ) {
                        viewModel.hideActionDialog()
                        viewModel.showEditDialogAction(list)
                    }

                    DomainListActionRow(
                        icon = Ico.ContentCopy,
                        label = stringResource(R.string.domain_list_copy)
                    ) {
                        copyToClipboard(context, list.name, viewModel.copyDomainsToClipboard(list))
                        viewModel.hideActionDialog()
                        Toast.makeText(context, copiedText, Toast.LENGTH_SHORT).show()
                    }

                    if (!list.isBuiltIn) {
                        DomainListActionRow(
                            icon = Icons.Default.Delete,
                            label = stringResource(R.string.delete),
                            destructive = true
                        ) {
                            viewModel.deleteList(list.id)
                            viewModel.hideActionDialog()
                        }
                    }
                }
            }
        }
    }
}

private fun parseDomains(raw: String): List<String> =
    raw.lines().map { it.trim() }.filter { it.isNotEmpty() }

@Suppress("UsePropertyAccessSyntax")
private fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
}

@Composable
private fun DomainListActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    destructive: Boolean = false,
    onClick: () -> Unit
) {
    val tint = if (destructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = tint
        )
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
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = domainList.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                val summary = domainList.domains.take(3).joinToString(", ")
                val displayText = if (domainList.domains.size > 3) {
                    "$summary…"
                } else {
                    summary
                }

                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.domain_list_domains_count,
                        domainList.domains.size
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
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
                    label = { Text(stringResource(R.string.domain_list_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = domains,
                    onValueChange = { domains = it },
                    label = { Text(stringResource(R.string.domain_list_domains)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10,
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, domains) },
                enabled = name.isNotBlank() && domains.isNotBlank()
            ) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
