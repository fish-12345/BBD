package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _restartAlt: ImageVector? = null

val IconsData.RestartAlt: ImageVector
    get() {
        if (_restartAlt != null) return _restartAlt!!
        _restartAlt = ImageVector.Builder(
            name = "RestartAlt",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 21.0f)
                quadToRelative(-3.45f, 0.0f, -6.012f, -2.287f)
                reflectiveQuadTo(3.05f, 13.0f)
                horizontalLineTo(5.1f)
                quadToRelative(0.35f, 2.6f, 2.313f, 4.3f)
                reflectiveQuadTo(12.0f, 19.0f)
                quadToRelative(2.925f, 0.0f, 4.963f, -2.037f)
                reflectiveQuadTo(19.0f, 12.0f)
                quadToRelative(0.0f, -2.925f, -2.037f, -4.963f)
                reflectiveQuadTo(12.0f, 5.0f)
                verticalLineToRelative(3.0f)
                lineTo(8.0f, 4.0f)
                lineToRelative(4.0f, -4.0f)
                verticalLineToRelative(3.0f)
                quadToRelative(3.75f, 0.0f, 6.375f, 2.625f)
                reflectiveQuadTo(21.0f, 12.0f)
                quadToRelative(0.0f, 3.75f, -2.625f, 6.375f)
                reflectiveQuadTo(12.0f, 21.0f)
                close()
            }
        }.build()
        return _restartAlt!!
    }
