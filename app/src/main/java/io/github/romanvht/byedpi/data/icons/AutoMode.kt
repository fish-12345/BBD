package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _autoMode: ImageVector? = null

val IconsData.AutoMode: ImageVector
    get() {
        if (_autoMode != null) return _autoMode!!
        _autoMode = ImageVector.Builder(
            name = "AutoMode",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19.35f, 9.04f)
                curveToRelative(-0.68f, -1.45f, -1.71f, -2.73f, -2.98f, -3.72f)
                lineToRelative(1.24f, -1.24f)
                curveToRelative(1.51f, 1.21f, 2.72f, 2.77f, 3.53f, 4.54f)
                lineTo(19.35f, 9.04f)
                close()
                moveTo(12.0f, 4.0f)
                verticalLineTo(2.0f)
                curveToRelative(1.85f, 0.0f, 3.58f, 0.48f, 5.08f, 1.32f)
                lineToRelative(-1.04f, 1.15f)
                curveTo(14.86f, 4.14f, 13.47f, 4.0f, 12.0f, 4.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-4.41f, 0.0f, -8.0f, -3.59f, -8.0f, -8.0f)
                curveToRelative(0.0f, -2.31f, 0.98f, -4.39f, 2.56f, -5.85f)
                lineTo(5.15f, 4.74f)
                curveTo(3.21f, 6.54f, 2.0f, 9.13f, 2.0f, 12.0f)
                curveToRelative(0.0f, 5.52f, 4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                curveToRelative(0.0f, -1.42f, -0.3f, -2.78f, -0.83f, -4.0f)
                lineToRelative(-1.9f, 0.62f)
                curveTo(19.74f, 9.6f, 20.0f, 10.77f, 20.0f, 12.0f)
                curveTo(20.0f, 16.41f, 16.41f, 20.0f, 12.0f, 20.0f)
                close()
                moveTo(12.0f, 8.0f)
                lineToRelative(-4.0f, 9.0f)
                horizontalLineToRelative(1.9f)
                lineToRelative(0.7f, -1.78f)
                horizontalLineToRelative(2.8f)
                lineToRelative(0.7f, 1.78f)
                horizontalLineTo(16.0f)
                lineTo(12.0f, 8.0f)
                close()
                moveTo(11.3f, 13.5f)
                lineToRelative(0.7f, -1.8f)
                lineToRelative(0.7f, 1.8f)
                horizontalLineTo(11.3f)
                close()
            }
        }.build()
        return _autoMode!!
    }
