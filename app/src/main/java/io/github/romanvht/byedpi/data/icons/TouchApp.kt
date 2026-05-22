package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _touchApp: ImageVector? = null

val IconsData.TouchApp: ImageVector
    get() {
        if (_touchApp != null) return _touchApp!!
        _touchApp = ImageVector.Builder(
            name = "TouchApp",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(18.19f, 12.44f)
            lineToRelative(-3.24f, -1.62f)
            curveToRelative(-0.12f, -0.06f, -0.27f, -0.1f, -0.42f, -0.1f)
            curveToRelative(-0.11f, 0.0f, -0.22f, 0.02f, -0.33f, 0.06f)
            lineToRelative(-2.2f, 0.73f)
            verticalLineTo(4.5f)
            curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
            reflectiveCurveTo(9.0f, 3.67f, 9.0f, 4.5f)
            verticalLineToRelative(10.74f)
            lineToRelative(-3.44f, -0.72f)
            curveToRelative(-0.06f, -0.01f, -0.13f, -0.02f, -0.19f, -0.02f)
            curveToRelative(-0.26f, 0.0f, -0.5f, 0.1f, -0.69f, 0.29f)
            lineTo(4.0f, 15.5f)
            lineToRelative(5.12f, 5.12f)
            curveTo(9.41f, 20.91f, 9.8f, 21.0f, 10.21f, 21.0f)
            horizontalLineToRelative(6.63f)
            curveToRelative(0.75f, 0.0f, 1.4f, -0.52f, 1.55f, -1.26f)
            lineToRelative(1.0f, -5.0f)
            curveTo(18.52f, 14.15f, 18.54f, 13.51f, 18.19f, 12.44f)
            close()
            moveTo(16.84f, 14.47f)
            lineToRelative(-1.0f, 5.0f)
            horizontalLineTo(10.21f)
            lineToRelative(-3.64f, -3.64f)
            lineTo(10.0f, 16.53f)
            verticalLineTo(4.5f)
            curveToRelative(0.0f, -0.28f, 0.22f, -0.5f, 0.5f, -0.5f)
            reflectiveCurveToRelative(0.5f, 0.22f, 0.5f, 0.5f)
            verticalLineToRelative(8.13f)
            lineToRelative(3.14f, -1.05f)
            lineToRelative(2.7f, 1.35f)
            curveTo(16.96f, 13.51f, 16.94f, 13.84f, 16.84f, 14.47f)
            close()
        }.build()
        return _touchApp!!
    }
