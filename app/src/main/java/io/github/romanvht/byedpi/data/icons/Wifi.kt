package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _wifi: ImageVector? = null

val IconsData.Wifi: ImageVector
    get() {
        if (_wifi != null) return _wifi!!
        _wifi = ImageVector.Builder(
            name = "Wifi",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 21.0f)
            lineTo(0.0f, 9.0f)
            quadToRelative(2.425f, -2.425f, 5.638f, -3.712f)
            quadTo(8.85f, 4.0f, 12.0f, 4.0f)
            reflectiveQuadToRelative(6.363f, 1.288f)
            quadTo(21.575f, 6.575f, 24.0f, 9.0f)
            close()
        }.build()
        return _wifi!!
    }
