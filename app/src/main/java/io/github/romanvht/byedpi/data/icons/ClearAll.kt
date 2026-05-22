package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _clearAll: ImageVector? = null

val IconsData.ClearAll: ImageVector
    get() {
        if (_clearAll != null) return _clearAll!!
        _clearAll = ImageVector.Builder(
            name = "ClearAll",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(5f, 13f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(-2f)
                lineTo(5f, 11f)
                verticalLineToRelative(2f)
                close()
                moveTo(7f, 17f)
                horizontalLineToRelative(12f)
                verticalLineToRelative(-2f)
                lineTo(7f, 15f)
                verticalLineToRelative(2f)
                close()
                moveTo(3f, 9f)
                horizontalLineToRelative(16f)
                verticalLineTo(7f)
                horizontalLineTo(3f)
                verticalLineToRelative(2f)
                close()
            }
        }.build()
        return _clearAll!!
    }
