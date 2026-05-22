package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _nightlight: ImageVector? = null

val IconsData.Nightlight: ImageVector
    get() {
        if (_nightlight != null) return _nightlight!!
        _nightlight = ImageVector.Builder(
            name = "Nightlight",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11.1f, 12.08f)
                curveToRelative(0.0f, -2.26f, 1.01f, -4.28f, 2.6f, -5.64f)
                curveToRelative(-0.83f, -0.28f, -1.73f, -0.44f, -2.67f, -0.44f)
                curveToRelative(-2.78f, 0.0f, -5.03f, 2.25f, -5.03f, 5.03f)
                curveToRelative(0.0f, 2.78f, 2.25f, 5.03f, 5.03f, 5.03f)
                curveToRelative(0.94f, 0.0f, 1.84f, -0.16f, 2.67f, -0.44f)
                curveToRelative(-1.59f, -1.36f, -2.6f, -3.38f, -2.6f, -3.54f)
                close()
            }
        }.build()
        return _nightlight!!
    }
