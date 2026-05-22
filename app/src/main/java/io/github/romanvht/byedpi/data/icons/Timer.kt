package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _timer: ImageVector? = null

val IconsData.Timer: ImageVector
    get() {
        if (_timer != null) return _timer!!
        _timer = ImageVector.Builder(
            name = "Timer",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.0f, 3.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(11.0f, 14.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(-2.0f)
                close()
                moveTo(12.0f, 22.0f)
                quadToRelative(-2.075f, 0.0f, -3.9f, -0.788f)
                quadToRelative(-1.825f, -0.787f, -3.175f, -2.137f)
                quadToRelative(-1.35f, -1.35f, -2.137f, -3.175f)
                quadTo(2.0f, 14.075f, 2.0f, 12.0f)
                quadToRelative(0.0f, -2.075f, 0.788f, -3.9f)
                quadToRelative(0.787f, -1.825f, 2.137f, -3.175f)
                quadToRelative(1.35f, -1.35f, 3.175f, -2.137f)
                quadTo(9.925f, 2.0f, 12.0f, 2.0f)
                quadToRelative(2.1f, 0.0f, 3.963f, 0.825f)
                quadToRelative(1.862f, 0.825f, 3.162f, 2.225f)
                lineToRelative(1.425f, -1.425f)
                lineToRelative(1.4f, 1.4f)
                lineToRelative(-1.4f, 1.4f)
                quadToRelative(1.325f, 1.3f, 2.138f, 3.138f)
                quadTo(22.0f, 11.4f, 22.0f, 13.5f)
                quadToRelative(0.0f, 2.075f, -0.788f, 3.9f)
                quadToRelative(-0.787f, 1.825f, -2.137f, 3.175f)
                quadToRelative(-1.35f, 1.35f, -3.175f, 2.137f)
                quadTo(14.075f, 22.0f, 12.0f, 22.0f)
                close()
                moveTo(12.0f, 20.0f)
                quadToRelative(3.35f, 0.0f, 5.675f, -2.325f)
                quadTo(20.0f, 15.35f, 20.0f, 12.0f)
                quadToRelative(0.0f, -3.35f, -2.325f, -5.675f)
                quadTo(15.35f, 4.0f, 12.0f, 4.0f)
                quadToRelative(-3.35f, 0.0f, -5.675f, 2.325f)
                quadTo(4.0f, 8.65f, 4.0f, 12.0f)
                quadToRelative(0.0f, 3.35f, 2.325f, 5.675f)
                quadTo(8.65f, 20.0f, 12.0f, 20.0f)
                close()
            }
        }.build()
        return _timer!!
    }
