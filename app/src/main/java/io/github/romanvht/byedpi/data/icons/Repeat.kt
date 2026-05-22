package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _repeat: ImageVector? = null

val IconsData.Repeat: ImageVector
    get() {
        if (_repeat != null) return _repeat!!
        _repeat = ImageVector.Builder(
            name = "Repeat",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(7.0f, 22.0f)
            quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
            quadTo(5.0f, 20.825f, 5.0f, 20.0f)
            verticalLineToRelative(-7.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(7.0f)
            horizontalLineToRelative(10.0f)
            verticalLineToRelative(-3.0f)
            lineToRelative(4.0f, 4.0f)
            lineToRelative(-4.0f, 4.0f)
            close()
            moveTo(3.0f, 7.0f)
            lineToRelative(4.0f, -4.0f)
            lineToRelative(4.0f, 4.0f)
            lineToRelative(-4.0f, 0.0f)
            verticalLineToRelative(3.0f)
            horizontalLineToRelative(10.0f)
            verticalLineToRelative(-7.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(7.0f)
            quadToRelative(0.0f, 0.825f, -0.587f, 1.413f)
            quadTo(17.825f, 12.0f, 17.0f, 12.0f)
            horizontalLineTo(7.0f)
            verticalLineToRelative(3.0f)
            close()
        }.build()
        return _repeat!!
    }
