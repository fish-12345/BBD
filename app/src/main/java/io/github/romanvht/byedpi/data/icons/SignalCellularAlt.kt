package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _signalCellularAlt: ImageVector? = null

val IconsData.SignalCellularAlt: ImageVector
    get() {
        if (_signalCellularAlt != null) return _signalCellularAlt!!
        _signalCellularAlt = ImageVector.Builder(
            name = "SignalCellularAlt",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17.0f, 22.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(22.0f)
                horizontalLineTo(17.0f)
                close()
                moveTo(11.0f, 22.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(22.0f)
                horizontalLineTo(11.0f)
                close()
                moveTo(5.0f, 22.0f)
                verticalLineTo(18.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(22.0f)
                horizontalLineTo(5.0f)
                close()
            }
        }.build()
        return _signalCellularAlt!!
    }
