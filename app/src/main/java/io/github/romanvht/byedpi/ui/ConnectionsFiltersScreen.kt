package io.github.romanvht.byedpi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.romanvht.byedpi.R
import io.github.romanvht.byedpi.data.icons.*
import io.github.romanvht.byedpi.ui.components.SettingsCard
import io.github.romanvht.byedpi.ui.components.SwitchPreference
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionKind
import io.github.romanvht.byedpi.ui.viewmodel.ConnectionsFiltersViewModel
import io.github.romanvht.byedpi.utility.isTv

/**
 * Отдельный экран фильтров для списка подключений.
 * Изменения сразу сохраняются в настройках и применяются при возврате.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ConnectionsFiltersScreen(
    viewModel: ConnectionsFiltersViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val isTv = remember { context.isTv() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.connections_filters_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Ico.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    TextButton(
                        onClick = viewModel::reset,
                        enabled = viewModel.activeFilterCount > 0
                    ) {
                        Text(stringResource(R.string.connections_filters_reset))
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item(key = "summary") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = if (viewModel.activeFilterCount == 0) {
                            stringResource(R.string.connections_filters_none)
                        } else {
                            stringResource(
                                R.string.connections_filters_active,
                                viewModel.activeFilterCount
                            )
                        },
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(R.string.connections_filters_sort_hint),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item(key = "state") {
                SettingsCard(
                    title = stringResource(R.string.connections_filters_section_state)
                ) {
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_closed),
                        summary = stringResource(R.string.connections_hide_closed_summary),
                        checked = viewModel.hideClosed,
                        onCheckedChange = viewModel::updateHideClosed,
                        icon = Ico.NetworkCheck
                    )
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_errors),
                        summary = stringResource(R.string.connections_hide_errors_summary),
                        checked = viewModel.hideErrors,
                        onCheckedChange = viewModel::updateHideErrors,
                        icon = Ico.Error
                    )
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_dns),
                        summary = stringResource(R.string.connections_hide_dns_summary),
                        checked = viewModel.hideDns,
                        onCheckedChange = viewModel::updateHideDns,
                        icon = Ico.Dns
                    )
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_unnamed),
                        summary = stringResource(R.string.connections_hide_unnamed_summary),
                        checked = viewModel.hideUnnamed,
                        onCheckedChange = viewModel::updateHideUnnamed,
                        icon = Ico.Language
                    )
                }
            }

            item(key = "protocol") {
                SettingsCard(
                    title = stringResource(R.string.connections_filters_section_protocol)
                ) {
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_tcp),
                        summary = stringResource(R.string.connections_hide_tcp_summary),
                        checked = viewModel.hideTcp,
                        onCheckedChange = viewModel::updateHideTcp,
                        icon = Ico.Speed
                    )
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_udp),
                        summary = stringResource(R.string.connections_hide_udp_summary),
                        checked = viewModel.hideUdp,
                        onCheckedChange = viewModel::updateHideUdp,
                        icon = Ico.SignalCellularAlt
                    )
                    SwitchPreference(
                        title = stringResource(R.string.connections_hide_unknown_kind),
                        summary = stringResource(R.string.connections_hide_unknown_kind_summary),
                        checked = viewModel.hideUnknownKind,
                        onCheckedChange = viewModel::updateHideUnknownKind,
                        icon = Ico.AltRoute
                    )
                }
            }

            item(key = "kinds") {
                SettingsCard(
                    title = stringResource(R.string.connections_hidden_kinds)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.connections_hidden_kinds_summary),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ConnectionKind.entries
                                .filter { it != ConnectionKind.Unknown }
                                .forEach { kind ->
                                    val hidden = kind in viewModel.hiddenKinds
                                    FilterChip(
                                        selected = hidden,
                                        onClick = { viewModel.toggleKind(kind) },
                                        label = { Text(kind.label ?: kind.name) }
                                    )
                                }
                        }
                    }
                }
            }
        }
    }
}
