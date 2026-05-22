package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _contentCopy: ImageVector? = null

val IconsData.ContentCopy: ImageVector
    get() {
        if (_contentCopy != null) return _contentCopy!!
        _contentCopy = ImageVector.Builder(
            name = "ContentCopy",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(5.0f, 21.0f)
                quadToRelative(-0.825f, 0.0f, -1.413f, -0.588f)
                reflectiveQuadTo(3.0f, 19.0f)
                verticalLineTo(5.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(14.0f)
                horizontalLineToRelative(11.0f)
                verticalLineToRelative(2.0f)
                close()
                moveToRelative(4.0f, -4.0f)
                quadToRelative(-0.825f, 0.0f, -1.413f, -0.588f)
                reflectiveQuadTo(7.0f, 15.0f)
                verticalLineTo(3.0f)
                quadToRelative(0.0f, -0.825f, 0.588f, -1.413f)
                reflectiveQuadTo(9.0f, 1.0f)
                horizontalLineToRelative(10.0f)
                quadToRelative(0.825f, 0.0f, 1.413f, 0.588f)
                reflectiveQuadTo(21.0f, 3.0f)
                verticalLineToRelative(12.0f)
                quadToRelative(0.0f, 0.825f, -0.588f, 1.413f)
                reflectiveQuadTo(19.0f, 17.0f)
                close()
                moveToRelative(0.0f, -2.0f)
                horizontalLineToRelative(10.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(9.0f)
                close()
                moveToRelative(0.0f, 0.0f)
                verticalLineTo(3.0f)
                close()
            }
        }.build()
        return _contentCopy!!
    }
