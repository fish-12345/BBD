package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _altRoute: ImageVector? = null

val IconsData.AltRoute: ImageVector
    get() {
        if (_altRoute != null) return _altRoute!!
        _altRoute = ImageVector.Builder(
            name = "AltRoute",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.0f, 21.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(4.5f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                verticalLineToRelative(-5.66f)
                lineToRelative(-2.24f, 2.24f)
                lineToRelative(-1.42f, -1.42f)
                lineTo(16.0f, 8.0f)
                lineToRelative(4.16f, 4.16f)
                lineToRelative(-1.42f, 1.42f)
                lineToRelative(-2.24f, -2.24f)
                verticalLineTo(17.0f)
                curveToRelative(0.0f, 2.21f, -1.79f, 4.0f, -4.0f, 4.0f)
                horizontalLineTo(9.0f)
                close()
                
                moveTo(11.0f, 3.0f)
                lineTo(6.84f, 7.16f)
                lineToRelative(1.42f, 1.42f)
                lineTo(10.5f, 6.34f)
                verticalLineTo(15.0f)
                curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
                horizontalLineTo(4.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(4.5f)
                curveToRelative(2.21f, 0.0f, 4.0f, -1.79f, 4.0f, -4.0f)
                verticalLineTo(6.34f)
                lineToRelative(2.24f, 2.24f)
                lineToRelative(1.42f, -1.42f)
                lineTo(12.0f, 3.0f)
                horizontalLineToRelative(-1.0f)
                close()
            }
        }.build()
        return _altRoute!!
    }
