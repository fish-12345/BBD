package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _playArrow: ImageVector? = null

val IconsData.PlayArrow: ImageVector
    get() {
        if (_playArrow != null) return _playArrow!!
        _playArrow = ImageVector.Builder(
            name = "PlayArrow",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8.0f, 19.0f)
                verticalLineTo(5.0f)
                lineToRelative(11.0f, 7.0f)
                close()
            }
        }.build()
        return _playArrow!!
    }
