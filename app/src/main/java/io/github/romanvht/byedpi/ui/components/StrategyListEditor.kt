package io.github.romanvht.byedpi.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.icons.*
import io.github.romanvht.byedpi.utility.isTv

/**
 * Редактор списка стратегий для «Подбора стратегий».
 *
 * Каждая стратегия — отдельная карточка с рамкой; по нажатию открывается
 * меню «копировать / изменить / удалить».
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrategyListEditor(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String = "",
    values: List<String>,
    onValuesChange: (List<String>) -> Unit,
    summary: String? = null,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    val context = LocalContext.current
    val isTv = remember { context.isTv() }
    val copiedText = stringResource(R.string.connections_copied)

    var showEditor by remember { mutableStateOf(false) }
    var tempValues by remember(values) { mutableStateOf(values) }
    var newItemValue by remember { mutableStateOf("") }
    var actionIndex by remember { mutableStateOf<Int?>(null) }
    var editIndex by remember { mutableStateOf<Int?>(null) }

    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Expanded, SheetValue.Hidden)
    )

    val onAdd = {
        if (newItemValue.isNotBlank()) {
            tempValues = tempValues + newItemValue.lines()
                .map { it.trim() }
                .filter { it.isNotBlank() }
            newItemValue = ""
        }
    }

    PreferenceItem(
        modifier = modifier,
        title = title,
        summary = summary ?: if (values.isEmpty()) {
            stringResource(R.string.empty)
        } else {
            stringResource(R.string.test_strategy_count, values.size)
        },
        enabled = enabled,
        icon = icon,
        onClick = {
            tempValues = values
            newItemValue = ""
            showEditor = true
        }
    )

    if (!showEditor) return

    val editorContent: @Composable (Boolean) -> Unit = { showTitle ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            if (showTitle) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }
            Text(
                text = stringResource(R.string.test_strategy_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemValue,
                    onValueChange = { newItemValue = it },
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.add)) },
                    placeholder = { Text(placeholder) },
                    singleLine = false,
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onAdd() }),
                    shape = MaterialTheme.shapes.medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onAdd,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (tempValues.isEmpty()) {
                Text(
                    text = stringResource(R.string.test_strategy_list_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 260.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(tempValues) { index, item ->
                        StrategyRow(
                            index = index,
                            value = item,
                            onClick = { actionIndex = index }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FilledTonalButton(
                    onClick = { showEditor = false },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    onClick = {
                        onValuesChange(tempValues)
                        showEditor = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }

    if (isTv) {
        AlertDialog(
            onDismissRequest = { showEditor = false },
            title = { Text(title) },
            text = {
                Column(modifier = Modifier.width(460.dp)) {
                    editorContent(false)
                }
            },
            confirmButton = {}
        )
    } else {
        ModalBottomSheet(
            onDismissRequest = { showEditor = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            editorContent(true)
        }
    }

    val actionTarget = actionIndex
    val actionItem = actionTarget?.let { tempValues.getOrNull(it) }

    if (actionTarget != null && actionItem != null) {
        StrategyActionDialog(
            strategy = actionItem,
            onDismiss = { actionIndex = null },
            onCopy = {
                copyToClipboard(context, actionItem)
                Toast.makeText(context, copiedText, Toast.LENGTH_SHORT).show()
                actionIndex = null
            },
            onEdit = {
                actionIndex = null
                editIndex = actionTarget
            },
            onDelete = {
                tempValues = tempValues.toMutableList().apply { removeAt(actionTarget) }
                actionIndex = null
            }
        )
    }

    val editTarget = editIndex
    val editItem = editTarget?.let { tempValues.getOrNull(it) }

    if (editTarget != null && editItem != null) {
        StrategyEditDialog(
            initialValue = editItem,
            onDismiss = { editIndex = null },
            onConfirm = { newValue ->
                tempValues = tempValues.toMutableList().apply { set(editTarget, newValue) }
                editIndex = null
            }
        )
    }
}

@Composable
private fun StrategyRow(
    index: Int,
    value: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${index + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(24.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                modifier = Modifier.weight(1f),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Ico.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StrategyActionDialog(
    strategy: String,
    onDismiss: () -> Unit,
    onCopy: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.test_strategy_actions)) },
        text = {
            Column {
                Text(
                    text = strategy,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                StrategyActionRow(
                    icon = Ico.ContentCopy,
                    label = stringResource(R.string.cmd_history_copy),
                    onClick = onCopy
                )
                StrategyActionRow(
                    icon = Icons.Default.Edit,
                    label = stringResource(R.string.edit),
                    onClick = onEdit
                )
                StrategyActionRow(
                    icon = Icons.Default.Delete,
                    label = stringResource(R.string.delete),
                    destructive = true,
                    onClick = onDelete
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun StrategyActionRow(
    icon: ImageVector,
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
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = tint
        )
    }
}

@Composable
private fun StrategyEditDialog(
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var value by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.test_strategy_edit_title)) },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 8,
                shape = MaterialTheme.shapes.medium
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(value.trim()) },
                enabled = value.isNotBlank()
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

@Suppress("UsePropertyAccessSyntax")
private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Strategy", text))
}
