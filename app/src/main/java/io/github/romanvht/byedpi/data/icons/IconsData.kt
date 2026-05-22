package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Базовый объект для иконок приложения.
 * Сами иконки определены в отдельных файлах как extension-свойства.
 */
object IconsData {
    operator fun get(name: String): ImageVector {
        return when (name) {
            "VpnKey" -> VpnKey
            "Router" -> Router
            "Terminal" -> Terminal
            "EditNote" -> EditNote
            "BugReport" -> BugReport
            "PowerSettingsNew" -> PowerSettingsNew
            "ArrowDownward" -> ArrowDownward
            "ArrowUpward" -> ArrowUpward
            "ArrowBack" -> ArrowBack
            "ContentPaste" -> ContentPaste
            "Delete" -> Delete
            "ClearAll" -> ClearAll
            "PushPin" -> PushPin
            "PushPinOutlined" -> PushPinOutlined
            "History" -> History
            "ContentCopy" -> ContentCopy
            "Wifi" -> Wifi
            "SignalCellularAlt" -> SignalCellularAlt
            "PlayArrow" -> PlayArrow
            "Error" -> Error
            "RestartAlt" -> RestartAlt
            "FileUpload" -> FileUpload
            "FileDownload" -> FileDownload
            "Code" -> Code
            "Storage" -> Storage
            "SettingsInputComponent" -> SettingsInputComponent
            "Dns" -> Dns
            "NetworkCheck" -> NetworkCheck
            "Speed" -> Speed
            "FilterList" -> FilterList
            "AppRegistration" -> AppRegistration
            "Language" -> Language
            "LightMode" -> LightMode
            "Nightlight" -> Nightlight
            "ColorLens" -> ColorLens
            "AutoFixHigh" -> AutoFixHigh
            "Numbers" -> Numbers
            "FormatIndentIncrease" -> FormatIndentIncrease
            "Timer" -> Timer
            "DeleteSweep" -> DeleteSweep
            "AltRoute" -> AltRoute
            "Description" -> Description
            "DomainDisabled" -> DomainDisabled
            "Security" -> Security
            "Block" -> Block
            "Checklist" -> Checklist
            "SyncAlt" -> SyncAlt
            "CallSplit" -> CallSplit
            "DataArray" -> DataArray
            "Http" -> Http
            "Https" -> Https
            "DirectionsRun" -> DirectionsRun
            "TextFields" -> TextFields
            "Abc" -> Abc
            "SpaceBar" -> SpaceBar
            "Repeat" -> Repeat
            "TouchApp" -> TouchApp
            "Sort" -> Sort
            "Visibility" -> Visibility
            "HourglassEmpty" -> HourglassEmpty
            "Notes" -> Notes
            "AutoMode" -> AutoMode
            "BatteryAlert" -> BatteryAlert
            "Source" -> Source
            "HistoryEdu" -> HistoryEdu
            "ChevronRight" -> ChevronRight
            else -> error("Icon '$name' not found in IconsData.")
        }
    }
}
