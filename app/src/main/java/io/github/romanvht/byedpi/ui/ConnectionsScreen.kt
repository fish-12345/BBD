package io.github.romanvht.byedpi.ui

import android.text.format.Formatter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.icons.*
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionEvent
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionProtocol
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionState
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionsViewModel
import io.github.romanvht.byedpi.utility.isTv
import kotlinx.coroutines.delay

/**
 * Живой упрощённый лог соединений, проходящих через прокси.
 *
 * Android не отдаёт имя приложения для конкретного соединения без root,
 * поэтому записи показываются по хосту (SNI) и адресу назначения.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionsScreen(
    viewModel: ConnectionsViewModel = viewModel(),
    onBack: () -> Unit,
    onManageGroups: () -> Unit
) {
    val context = LocalContext.current
    val isTv = remember { context.isTv() }

    var nowEpoch by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            nowEpoch = System.currentTimeMillis()
            delay(1000)
        }
    }

    DisposableEffect(Unit) {
        viewModel.startMonitoring()
        onDispose { viewModel.stopMonitoring() }
    }

    var selectedId by remember { mutableStateOf<Long?>(null) }
    val selected = selectedId?.let { id -> viewModel.events.firstOrNull { it.id == id } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.connections_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Ico.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = viewModel::clear,
                        enabled = viewModel.events.isNotEmpty()
                    ) {
                        Icon(
                            Ico.DeleteSweep,
                            contentDescription = stringResource(R.string.connections_clear)
                        )
                    }
                    IconButton(onClick = onManageGroups) {
                        Icon(
                            Ico.EditNote,
                            contentDescription = stringResource(R.string.connections_manage)
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                horizontal = if (isTv) 48.dp else 16.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(key = "header") {
                ConnectionsHeader(viewModel)
            }

            item(key = "filters") {
                ConnectionFilters(viewModel)
            }

            val visible = viewModel.visibleEvents()
            if (visible.isEmpty()) {
                item(key = "empty") {
                    ConnectionsEmpty(
                        isRunning = viewModel.isRunning,
                        hasEvents = viewModel.events.isNotEmpty()
                    )
                }
            } else {
                items(visible, key = { it.id }) { event ->
                    ConnectionRow(
                        event = event,
                        nowEpoch = nowEpoch,
                        onClick = { selectedId = event.id }
                    )
                }
            }

            item(key = "note") {
                Text(
                    text = stringResource(R.string.connections_no_apps),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    if (selected != null) {
        ConnectionDetailsSheet(
            event = selected,
            nowEpoch = nowEpoch,
            onDismiss = { selectedId = null }
        )
    }
}

@Composable
private fun ConnectionsHeader(viewModel: ConnectionsViewModel) {
    val isRunning = viewModel.isRunning

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isRunning) Ico.NetworkCheck else Ico.PowerSettingsNew,
                    contentDescription = null,
                    tint = if (isRunning) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(
                        if (isRunning) R.string.connections_live
                        else R.string.connections_off
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (isRunning) {
                    TextButton(onClick = viewModel::togglePause) {
                        Text(
                            stringResource(
                                if (viewModel.isPaused) R.string.connections_resume
                                else R.string.connections_pause
                            )
                        )
                    }
                }
            }

            if (!isRunning) {
                Text(
                    text = stringResource(R.string.connections_off_hint),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatTile(
                        value = viewModel.totalCount,
                        label = stringResource(R.string.connections_total_label),
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        value = viewModel.openCount,
                        label = stringResource(R.string.connections_open_label),
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        value = viewModel.errorCount,
                        label = stringResource(R.string.connections_errors_label),
                        modifier = Modifier.weight(1f),
                        highlight = viewModel.errorCount > 0
                    )
                }

                if (viewModel.isPaused) {
                    Text(
                        text = stringResource(R.string.connections_paused_hint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectionFilters(viewModel: ConnectionsViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            selected = viewModel.hideClosed,
            onClick = viewModel::toggleHideClosed,
            label = { Text(stringResource(R.string.connections_hide_closed)) }
        )
        FilterChip(
            selected = viewModel.hideDns,
            onClick = viewModel::toggleHideDns,
            label = { Text(stringResource(R.string.connections_hide_dns)) },
            leadingIcon = {
                Icon(
                    Ico.Dns,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )
    }
}

@Composable
private fun StatTile(
    value: Int,
    label: String,
    modifier: Modifier = Modifier,
    highlight: Boolean = false
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = if (highlight) {
            MaterialTheme.colorScheme.errorContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (highlight) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                maxLines = 1
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (highlight) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ConnectionRow(
    event: ConnectionEvent,
    nowEpoch: Long,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val accent = accentFor(event.state)
    val status = statusLabel(event.state)
    val duration = formatDuration(
        event.durationMs(nowEpoch),
        isOpen = event.endEpoch == null
    )

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = accent.copy(alpha = 0.14f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = iconFor(event.state),
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = event.host.ifBlank {
                            stringResource(R.string.connections_unknown_host)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (event.protocol == ConnectionProtocol.Udp) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Badge(stringResource(R.string.connections_udp))
                    }
                    if (event.isDns) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Badge(stringResource(R.string.connections_dns_badge))
                    }
                }

                val destination = event.destination
                if (destination.isNotBlank() && destination != event.host) {
                    Text(
                        text = destination,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val detail = event.detail?.takeIf { it.isNotBlank() }
                if (detail != null) {
                    Text(
                        text = detail,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (event.state == ConnectionState.Error) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val downloaded = event.downloaded
                val uploaded = event.uploaded
                if (downloaded != null || uploaded != null) {
                    Text(
                        text = listOfNotNull(
                            downloaded?.takeIf { it > 0 }?.let {
                                stringResource(
                                    R.string.connections_downloaded_short,
                                    Formatter.formatShortFileSize(context, it)
                                )
                            },
                            uploaded?.takeIf { it > 0 }?.let {
                                stringResource(
                                    R.string.connections_uploaded_short,
                                    Formatter.formatShortFileSize(context, it)
                                )
                            }
                        ).joinToString("  "),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                if (event.time.isNotBlank()) {
                    Text(
                        text = event.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = status,
                    style = MaterialTheme.typography.labelSmall,
                    color = accent,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Text(
                    text = duration,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun Badge(text: String) {
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConnectionDetailsSheet(
    event: ConnectionEvent,
    nowEpoch: Long,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val accent = accentFor(event.state)

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = accent.copy(alpha = 0.14f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = iconFor(event.state),
                            contentDescription = null,
                            tint = accent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = event.host.ifBlank {
                            stringResource(R.string.connections_unknown_host)
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = statusLabel(event.state),
                        style = MaterialTheme.typography.labelMedium,
                        color = accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (event.destination.isNotBlank()) {
                DetailRow(
                    label = stringResource(R.string.connections_label_destination),
                    value = event.destination,
                    monospace = true
                )
            }
            DetailRow(
                label = stringResource(R.string.connections_label_protocol),
                value = if (event.protocol == ConnectionProtocol.Udp) {
                    stringResource(R.string.connections_udp)
                } else {
                    "TCP"
                }
            )
            DetailRow(
                label = stringResource(R.string.connections_label_started),
                value = event.time
            )
            DetailRow(
                label = stringResource(R.string.connections_label_duration),
                value = formatDuration(
                    event.durationMs(nowEpoch),
                    isOpen = event.endEpoch == null
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            val closed = event.endEpoch != null
            DetailRow(
                label = stringResource(R.string.connections_label_downloaded),
                value = formatBytes(context, event.downloaded, closed),
                icon = Ico.ArrowDownward
            )
            DetailRow(
                label = stringResource(R.string.connections_label_uploaded),
                value = formatBytes(context, event.uploaded, closed),
                icon = Ico.ArrowUpward
            )
            DetailRow(
                label = stringResource(R.string.connections_label_requests),
                value = event.requests?.toString() ?: if (closed) "0" else PENDING,
                icon = Ico.Repeat
            )

            if (event.endEpoch == null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.connections_details_pending),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            val detail = event.detail?.takeIf { it.isNotBlank() }
            if (detail != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (event.state == ConnectionState.Error) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.connections_close))
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    monospace: Boolean = false,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = if (monospace) {
                MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace)
            } else {
                MaterialTheme.typography.bodySmall
            },
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ConnectionsEmpty(isRunning: Boolean, hasEvents: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (hasEvents) Ico.FilterList else Ico.Language,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outlineVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                when {
                    hasEvents -> R.string.connections_filtered_empty
                    isRunning -> R.string.connections_empty
                    else -> R.string.connections_off
                }
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(
                when {
                    hasEvents -> R.string.connections_filtered_empty_hint
                    isRunning -> R.string.connections_empty_hint
                    else -> R.string.connections_off_hint
                }
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

private fun formatBytes(context: android.content.Context, value: Long?, closed: Boolean): String =
    when {
        value != null && value > 0 -> Formatter.formatShortFileSize(context, value)
        closed -> Formatter.formatShortFileSize(context, 0L)
        else -> PENDING
    }

@Composable
private fun formatDuration(durationMs: Long, isOpen: Boolean): String {
    val seconds = ((durationMs + 999) / 1000).coerceAtLeast(1)
    val value = if (seconds < 60) {
        stringResource(R.string.connections_seconds, seconds)
    } else {
        "%d:%02d".format(seconds / 60, seconds % 60)
    }
    return if (isOpen) {
        stringResource(R.string.connections_open_for, value)
    } else {
        value
    }
}

@Composable
private fun accentFor(state: ConnectionState): Color = when (state) {
    ConnectionState.Started -> MaterialTheme.colorScheme.primary
    ConnectionState.Desync -> MaterialTheme.colorScheme.tertiary
    ConnectionState.Closed -> SuccessColor
    ConnectionState.Timeout -> MaterialTheme.colorScheme.tertiary
    ConnectionState.Error -> MaterialTheme.colorScheme.error
}

private fun iconFor(state: ConnectionState) = when (state) {
    ConnectionState.Started -> Ico.SyncAlt
    ConnectionState.Desync -> Ico.AutoFixHigh
    ConnectionState.Closed -> Ico.NetworkCheck
    ConnectionState.Timeout -> Ico.Timer
    ConnectionState.Error -> Ico.Error
}

@Composable
private fun statusLabel(state: ConnectionState): String = stringResource(
    when (state) {
        ConnectionState.Started -> R.string.connections_status_started
        ConnectionState.Desync -> R.string.connections_status_desync
        ConnectionState.Closed -> R.string.connections_status_closed
        ConnectionState.Timeout -> R.string.connections_status_timeout
        ConnectionState.Error -> R.string.connections_status_error
    }
)

private val SuccessColor = Color(0xFF4CAF50)
private const val PENDING = "—"
