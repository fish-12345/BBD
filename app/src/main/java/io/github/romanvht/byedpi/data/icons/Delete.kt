package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _delete: ImageVector? = null

val IconsData.Delete: ImageVector
    get() {
        if (_delete != null) return _delete!!
        _delete = ImageVector.Builder(
            name = "Delete",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.0f, 21.0f)
                quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
                quadTo(5.0f, 19.825f, 5.0f, 19.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(4.0f)
                horizontalLineToRelative(5.0f)
                verticalLineTo(3.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(1.0f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(-1.0f)
                verticalLineToRelative(13.0f)
                quadToRelative(0.0f, 0.825f, -0.587f, 1.413f)
                quadTo(17.825f, 21.0f, 17.0f, 21.0f)
                close()
                moveTo(17.0f, 6.0f)
                horizontalLineTo(7.0f)
                verticalLineToRelative(13.0f)
                horizontalLineToRelative(10.0f)
                close()
                moveTo(9.0f, 17.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(9.0f)
                close()
                moveTo(13.0f, 17.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(-2.0f)
                close()
                moveTo(7.0f, 6.0f)
                verticalLineToRelative(13.0f)
                close()
            }
        }.build()
        return _delete!!
    }
