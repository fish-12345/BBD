package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _Code: ImageVector? = null

val IconsData.Code: ImageVector
    get() {
        if (_Code != null) return _Code!!
        _Code = ImageVector.Builder(
            name = "Code",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(9.4f, 16.6f)
            lineTo(4.8f, 12.0f)
            lineTo(9.4f, 7.4f)
            lineTo(8.0f, 6.0f)
            lineTo(2.0f, 12.0f)
            lineTo(8.0f, 18.0f)
            close()
            moveTo(14.6f, 16.6f)
            lineTo(16.0f, 18.0f)
            lineTo(22.0f, 12.0f)
            lineTo(16.0f, 6.0f)
            lineTo(14.6f, 7.4f)
            lineTo(19.2f, 12.0f)
            close()
        }
        .build()
        return _Code!!
    }
