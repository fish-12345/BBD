package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _deleteSweep: ImageVector? = null

val IconsData.DeleteSweep: ImageVector
    get() {
        if (_deleteSweep != null) return _deleteSweep!!
        _deleteSweep = ImageVector.Builder(
            name = "DeleteSweep",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15f, 16f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-4f)
                verticalLineToRelative(-2f)
                close()
                moveTo(15f, 8f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-7f)
                verticalLineTo(8f)
                close()
                moveTo(15f, 12f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-6f)
                verticalLineToRelative(-2f)
                close()
                
                moveTo(3f, 18f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(6f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                verticalLineTo(8f)
                horizontalLineTo(3f)
                verticalLineToRelative(10f)
                close()
                moveTo(5f, 10f)
                horizontalLineToRelative(6f)
                verticalLineToRelative(8f)
                horizontalLineTo(5f)
                verticalLineToRelative(-8f)
                close()
                
                moveTo(14f, 5f)
                horizontalLineToRelative(-3f)
                lineToRelative(-1f, -1f)
                horizontalLineTo(6f)
                lineTo(5f, 5f)
                horizontalLineTo(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(12f)
                verticalLineTo(5f)
                close()
            }
        }.build()
        return _deleteSweep!!
    }
