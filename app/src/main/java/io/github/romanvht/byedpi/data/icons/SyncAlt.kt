package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _syncAlt: ImageVector? = null

val IconsData.SyncAlt: ImageVector
    get() {
        if (_syncAlt != null) return _syncAlt!!
        _syncAlt = ImageVector.Builder(
            name = "SyncAlt",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(18.0f, 12.0f)
            lineToRelative(-1.41f, 1.41f)
            lineTo(18.17f, 15.0f)
            horizontalLineTo(7.0f)
            verticalLineToRelative(2.0f)
            horizontalLineToRelative(11.17f)
            lineToRelative(-1.58f, 1.59f)
            lineTo(18.0f, 20.0f)
            lineToRelative(4.0f, -4.0f)
            close()
            moveTo(6.0f, 12.0f)
            lineToRelative(-4.0f, 4.0f)
            lineToRelative(4.0f, 4.0f)
            lineToRelative(1.41f, -1.41f)
            lineTo(5.83f, 17.0f)
            horizontalLineTo(17.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(5.83f)
            lineToRelative(1.58f, -1.59f)
            close()
        }.build()
        return _syncAlt!!
    }
