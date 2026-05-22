package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _http: ImageVector? = null

val IconsData.Http: ImageVector
    get() {
        if (_http != null) return _http!!
        _http = ImageVector.Builder(
            name = "Http",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(4.5f, 11.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(1.0f)
                verticalLineToRelative(6.0f)
                horizontalLineTo(1.5f)
                verticalLineToRelative(-2.5f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(4.5f)
                close()
                
                moveTo(7.0f, 10.5f)
                horizontalLineToRelative(1.5f)
                verticalLineTo(15.0f)
                horizontalLineTo(10.0f)
                verticalLineToRelative(-4.5f)
                horizontalLineToRelative(1.5f)
                verticalLineTo(9.0f)
                horizontalLineTo(7.0f)
                close()
                
                moveTo(12.5f, 10.5f)
                horizontalLineToRelative(1.5f)
                verticalLineTo(15.0f)
                horizontalLineTo(15.5f)
                verticalLineToRelative(-4.5f)
                horizontalLineTo(17.0f)
                verticalLineTo(9.0f)
                horizontalLineToRelative(-4.5f)
                close()
                
                moveTo(18.0f, 9.0f)
                verticalLineToRelative(4.5f)
                horizontalLineToRelative(2.5f)
                curveToRelative(0.83f, 0.0f, 1.5f, -0.67f, 1.5f, -1.5f)
                verticalLineToRelative(-1.5f)
                curveToRelative(0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f)
                horizontalLineTo(18.0f)
                close()
                moveTo(20.5f, 12.0f)
                horizontalLineToRelative(-1.0f)
                verticalLineToRelative(-1.5f)
                horizontalLineToRelative(1.0f)
                close()
            }
        }.build()
        return _http!!
    }
