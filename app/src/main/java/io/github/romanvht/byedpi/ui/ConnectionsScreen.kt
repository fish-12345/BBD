package io.github.romanvht.byedpi.ui

import android.text.format.Formatter
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionGroup
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionKind
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
    onOpenFilters: () -> Unit
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
    val expandedGroups = remember { mutableStateMapOf<String, Boolean>() }

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
                    IconButton(onClick = onOpenFilters) {
                        BadgedBox(
                            badge = {
                                if (viewModel.activeFilterCount > 0) {
                                    Badge {
                                        Text(viewModel.activeFilterCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Ico.FilterList,
                                contentDescription = stringResource(R.string.connections_filters_title)
                            )
                        }
                    }
                    IconButton(
                        onClick = viewModel::clear,
                        enabled = viewModel.events.isNotEmpty()
                    ) {
                        Icon(
                            Ico.DeleteSweep,
                            contentDescription = stringResource(R.string.connections_clear)
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

            val groups = viewModel.groupedEvents()
            if (groups.isEmpty()) {
                item(key = "empty") {
                    ConnectionsEmpty(
                        isRunning = viewModel.isRunning,
                        hasEvents = viewModel.events.isNotEmpty()
                    )
                }
            } else {
                items(groups, key = { it.key }) { group ->
                    if (group.size == 1) {
                        val event = group.entries.first()
                        ConnectionRow(
                            event = event,
                            nowEpoch = nowEpoch,
                            onClick = { selectedId = event.id }
                        )
                    } else {
                        ConnectionGroupCard(
                            group = group,
                            nowEpoch = nowEpoch,
                            expanded = expandedGroups[group.key] == true,
                            onToggle = {
                                expandedGroups[group.key] =
                                    !(expandedGroups[group.key] ?: false)
                            },
                            onSelect = { selectedId = it.id }
                        )
                    }
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
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CompactStat(
                            value = viewModel.totalCount,
                            label = stringResource(R.string.connections_total_label),
                            modifier = Modifier.weight(1f)
                        )
                        VerticalDivider(
                            modifier = Modifier.height(22.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        CompactStat(
                            value = viewModel.openCount,
                            label = stringResource(R.string.connections_open_label),
                            modifier = Modifier.weight(1f),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        VerticalDivider(
                            modifier = Modifier.height(22.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        CompactStat(
                            value = viewModel.errorCount,
                            label = stringResource(R.string.connections_errors_label),
                            modifier = Modifier.weight(1f),
                            tint = if (viewModel.errorCount > 0) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
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
private fun CompactStat(
    value: Int,
    label: String,
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = tint ?: MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
                        KindBadge(stringResource(R.string.connections_udp))
                    }
                    event.kind.label?.let { label ->
                        Spacer(modifier = Modifier.width(6.dp))
                        KindBadge(label)
                    }
                    if (event.isDns && event.kind != ConnectionKind.Dns) {
                        Spacer(modifier = Modifier.width(6.dp))
                        KindBadge(stringResource(R.string.connections_dns_badge))
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
private fun ConnectionGroupCard(
    group: ConnectionGroup,
    nowEpoch: Long,
    expanded: Boolean,
    onToggle: () -> Unit,
    onSelect: (ConnectionEvent) -> Unit
) {
    val context = LocalContext.current
    val accent = when {
        group.isOpen -> MaterialTheme.colorScheme.primary
        group.hasError -> MaterialTheme.colorScheme.error
        else -> SuccessColor
    }
    val status = stringResource(
        when {
            group.isOpen -> R.string.connections_group_active
            group.hasError -> R.string.connections_group_errors
            else -> R.string.connections_group_closed
        }
    )
    val kinds = group.kinds.mapNotNull { it.label }.joinToString(" · ")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggle)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = accent.copy(alpha = 0.14f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = group.size.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = accent
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = group.title,
                            style = if (group.byHost) {
                                MaterialTheme.typography.bodyMedium
                            } else {
                                MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace
                                )
                            },
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (kinds.isNotBlank()) {
                            Spacer(modifier = Modifier.width(6.dp))
                            KindBadge(kinds)
                        }
                        if (group.isDns && ConnectionKind.Dns !in group.kinds) {
                            Spacer(modifier = Modifier.width(6.dp))
                            KindBadge(stringResource(R.string.connections_dns_badge))
                        }
                    }

                    if (group.byHost) {
                        if (group.ips.isNotEmpty()) {
                            Text(
                                text = stringResource(
                                    R.string.connections_group_ips,
                                    group.ips.take(3).joinToString(", ")
                                ),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    } else if (group.hosts.isNotEmpty()) {
                        Text(
                            text = stringResource(
                                R.string.connections_group_hosts,
                                group.hosts.take(3).joinToString(", ")
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    val downloaded = group.downloaded
                    val uploaded = group.uploaded
                    if (downloaded > 0 || uploaded > 0) {
                        Text(
                            text = listOfNotNull(
                                downloaded.takeIf { it > 0 }?.let {
                                    stringResource(
                                        R.string.connections_downloaded_short,
                                        Formatter.formatShortFileSize(context, it)
                                    )
                                },
                                uploaded.takeIf { it > 0 }?.let {
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
                    Text(
                        text = status,
                        style = MaterialTheme.typography.labelSmall,
                        color = accent,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Icon(
                        imageVector = Ico.ChevronRight,
                        contentDescription = stringResource(
                            if (expanded) R.string.connections_group_collapse
                            else R.string.connections_group_expand
                        ),
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(if (expanded) 90f else 0f),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (expanded) {
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    group.entries
                        .sortedWith(compareBy({ stateRankFor(it) }, { -it.startEpoch }))
                        .forEach { event ->
                            ConnectionRow(
                                event = event,
                                nowEpoch = nowEpoch,
                                onClick = { onSelect(event) }
                            )
                        }
                }
            }
        }
    }
}

private fun stateRankFor(event: ConnectionEvent): Int = when {
    event.isOpen -> 0
    event.state == ConnectionState.Error || event.state == ConnectionState.Timeout -> 1
    else -> 2
}

@Composable
private fun KindBadge(text: String) {
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

            SelectionContainer {
                Column {
                    if (event.hasName) {
                        DetailRow(
                            label = stringResource(R.string.connections_label_host),
                            value = event.host,
                            copyable = true
                        )
                    }
                    if (event.destination.isNotBlank()) {
                        DetailRow(
                            label = stringResource(R.string.connections_label_destination),
                            value = event.destination,
                            monospace = true,
                            copyable = true
                        )
                    }
                    DetailRow(
                        label = stringResource(R.string.connections_label_protocol),
                        value = run {
                            val base = if (event.protocol == ConnectionProtocol.Udp) {
                                stringResource(R.string.connections_udp)
                            } else {
                                "TCP"
                            }
                            event.kind.label?.let { "$base · $it" } ?: base
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
                }
            }

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
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    copyable: Boolean = false
) {
    val context = LocalContext.current
    val copiedText = stringResource(R.string.connections_copied)
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
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(2f, fill = false)
        )
        if (copyable) {
            IconButton(
                onClick = {
                    copyToClipboard(context, label, value)
                    Toast.makeText(context, copiedText, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Ico.ContentCopy,
                    contentDescription = stringResource(R.string.connections_copy),
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Suppress("UsePropertyAccessSyntax")
private fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
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
