package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _networkCheck: ImageVector? = null

val IconsData.NetworkCheck: ImageVector
    get() {
        if (_networkCheck != null) return _networkCheck!!
        _networkCheck = ImageVector.Builder(
            name = "NetworkCheck",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15.91f, 13.0f)
                horizontalLineTo(21.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-5.09f)
                curveToRelative(-0.43f, -2.32f, -2.22f, -4.11f, -4.54f, -4.54f)
                verticalLineTo(1.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(5.46f)
                curveToRelative(-2.32f, 0.43f, -4.11f, 2.22f, -4.54f, 4.54f)
                horizontalLineTo(0.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(4.87f)
                curveToRelative(0.36f, 1.93f, 1.7f, 3.53f, 3.51f, 4.25f)
                lineToRelative(-2.6f, 2.6f)
                lineTo(7.2f, 21.2f)
                lineToRelative(2.55f, -2.55f)
                curveToRelative(0.95f, 0.87f, 2.19f, 1.35f, 3.51f, 1.35f)
                curveToRelative(2.8f, 0.0f, 5.16f, -2.01f, 5.65f, -4.65f)
                lineToRelative(1.88f, 1.88f)
                lineToRelative(1.41f, -1.41f)
                lineTo(15.91f, 13.0f)
                close()
                moveTo(11.27f, 18.37f)
                curveToRelative(-2.07f, -0.15f, -3.76f, -1.84f, -3.91f, -3.91f)
                lineToRelative(2.48f, -2.48f)
                curveToRelative(0.18f, 0.23f, 0.41f, 0.43f, 0.68f, 0.58f)
                curveToRelative(0.33f, 0.19f, 0.7f, 0.29f, 1.08f, 0.29f)
                curveToRelative(0.38f, 0.0f, 0.75f, -0.1f, 1.08f, -0.29f)
                curveToRelative(0.27f, -0.15f, 0.5f, -0.35f, 0.68f, -0.58f)
                lineToRelative(2.48f, 2.48f)
                curveTo(13.11f, 16.53f, 11.42f, 18.22f, 11.27f, 18.37f)
                close()
            }
        }.build()
        return _networkCheck!!
    }
