package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _directionsRun: ImageVector? = null

val IconsData.DirectionsRun: ImageVector
    get() {
        if (_directionsRun != null) return _directionsRun!!
        _directionsRun = ImageVector.Builder(
            name = "DirectionsRun",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.49f, 5.48f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(9.53f, 11.0f)
                lineTo(8.43f, 16.33f)
                lineToRelative(-2.14f, -0.42f)
                lineToRelative(0.44f, -2.12f)
                lineToRelative(-2.54f, -0.5f)
                curveToRelative(-0.43f, -0.09f, -0.7f, -0.52f, -0.62f, -0.95f)
                lineToRelative(0.79f, -3.81f)
                curveToRelative(0.12f, -0.58f, 0.62f, -1.03f, 1.25f, -1.13f)
                lineToRelative(4.3f, -0.66f)
                curveToRelative(0.63f, -0.1f, 1.23f, 0.2f, 1.54f, 0.74f)
                lineToRelative(1.36f, 2.39f)
                curveToRelative(0.9f, 1.59f, 2.65f, 2.67f, 4.66f, 2.67f)
                verticalLineToRelative(2.0f)
                curveToRelative(-2.5f, 0.0f, -4.68f, -1.35f, -5.87f, -3.35f)
                lineToRelative(-0.46f, 2.22f)
                lineToRelative(2.38f, 2.38f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-5.0f)
                lineToRelative(-1.99f, -1.99f)
                lineToRelative(-0.54f, 2.61f)
                lineToRelative(-4.13f, 2.33f)
                lineToRelative(-1.0f, -1.74f)
                lineToRelative(3.1f, -1.75f)
                lineToRelative(1.17f, -5.61f)
                close()
            }
        }.build()
        return _directionsRun!!
    }
