package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.vector.ImageVector

object Ico {
    operator fun get(name: String): ImageVector {
        return when (name) {
            "Abc" -> Abc
            "AltRoute" -> AltRoute
            "AppRegistration" -> AppRegistration
            "ArrowBack" -> ArrowBack
            "ArrowDownward" -> ArrowDownward
            "ArrowUpward" -> ArrowUpward
            "AutoFixHigh" -> AutoFixHigh
            "AutoMode" -> AutoMode
            "BatteryAlert" -> BatteryAlert
            "Block" -> Block
            "BugReport" -> BugReport
            "CallSplit" -> CallSplit
            "Checklist" -> Checklist
            "ChevronRight" -> ChevronRight
            "ClearAll" -> ClearAll
            "Code" -> Code
            "ColorLens" -> ColorLens
            "ContentCopy" -> ContentCopy
            "ContentPaste" -> ContentPaste
            "DataArray" -> DataArray
            "Delete" -> Delete
            "DeleteSweep" -> DeleteSweep
            "Description" -> Description
            "DirectionsRun" -> DirectionsRun
            "Dns" -> Dns
            "DomainDisabled" -> DomainDisabled
            "EditNote" -> EditNote
            "Error" -> Error
            "FileDownload" -> FileDownload
            "FileUpload" -> FileUpload
            "FilterList" -> FilterList
            "FormatIndentIncrease" -> FormatIndentIncrease
            "History" -> History
            "HistoryEdu" -> HistoryEdu
            "HourglassEmpty" -> HourglassEmpty
            "Http" -> Http
            "Https" -> Https
            "Language" -> Language
            "LightMode" -> LightMode
            "NetworkCheck" -> NetworkCheck
            "Nightlight" -> Nightlight
            "Notes" -> Notes
            "Numbers" -> Numbers
            "PlayArrow" -> PlayArrow
            "PowerSettingsNew" -> PowerSettingsNew
            "PushPin" -> PushPin
            "Repeat" -> Repeat
            "RestartAlt" -> RestartAlt
            "Router" -> Router
            "Security" -> Security
            "SettingsInputComponent" -> SettingsInputComponent
            "SignalCellularAlt" -> SignalCellularAlt
            "Sort" -> Sort
            "Source" -> Source
            "SpaceBar" -> SpaceBar
            "Speed" -> Speed
            "Storage" -> Storage
            "SyncAlt" -> SyncAlt
            "Terminal" -> Terminal
            "TextFields" -> TextFields
            "Timer" -> Timer
            "TouchApp" -> TouchApp
            "Visibility" -> Visibility
            "VpnKey" -> VpnKey
            "Wifi" -> Wifi
            else -> throw IllegalArgumentException("Unknown icon: $name")
        }
    }
}