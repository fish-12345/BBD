package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _numbers: ImageVector? = null

val IconsData.Numbers: ImageVector
    get() {
        if (_numbers != null) return _numbers!!
        _numbers = ImageVector.Builder(
            name = "Numbers",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            // Path data for "123" Material Symbol
            moveTo(7f, 15f)
            horizontalLineToRelative(2f)
            verticalLineTo(9f)
            horizontalLineTo(7f)
            lineToRelative(-1.5f, 1.5f)
            lineTo(6.5f, 11.5f)
            lineTo(7f, 11f)
            close()
            
            moveTo(13.5f, 15f)
            horizontalLineTo(10f)
            verticalLineToRelative(-1.5f)
            horizontalLineToRelative(2f)
            verticalLineTo(12f)
            horizontalLineToRelative(-1f)
            verticalLineToRelative(-1.5f)
            horizontalLineToRelative(1f)
            verticalLineToRelative(-0.5f)
            horizontalLineToRelative(-2f)
            verticalLineTo(8.5f)
            horizontalLineToRelative(3.5f)
            verticalLineTo(11f)
            curveToRelative(0f, 0.55f, -0.45f, 1f, -1f, 1f)
            curveToRelative(0.55f, 0f, 1f, 0.45f, 1f, 1f)
            close()
            
            moveTo(18f, 15f)
            horizontalLineToRelative(-3.5f)
            verticalLineToRelative(-1.5f)
            horizontalLineToRelative(2f)
            verticalLineTo(12f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(-1.5f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(-0.5f)
            horizontalLineToRelative(-2f)
            verticalLineTo(8.5f)
            horizontalLineTo(18f)
            verticalLineTo(11f)
            curveToRelative(0f, 0.55f, -0.45f, 1f, -1f, 1f)
            curveToRelative(0.55f, 0f, 1f, 0.45f, 1f, 1f)
            close()
        }.build()
        return _numbers!!
    }
