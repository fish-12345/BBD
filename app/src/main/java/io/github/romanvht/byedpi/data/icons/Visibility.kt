package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _visibility: ImageVector? = null

val IconsData.Visibility: ImageVector
    get() {
        if (_visibility != null) return _visibility!!
        _visibility = ImageVector.Builder(
            name = "Visibility",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 18.0f)
                quadToRelative(-4.275f, 0.0f, -7.637f, -2.312f)
                reflectiveQuadTo(0.0f, 10.0f)
                quadToRelative(1.5f, -3.875f, 4.863f, -6.188f)
                reflectiveQuadTo(12.0f, 1.5f)
                quadToRelative(4.275f, 0.0f, 7.638f, 2.312f)
                reflectiveQuadTo(24.0f, 10.0f)
                quadToRelative(-1.5f, 3.875f, -4.862f, 6.188f)
                reflectiveQuadTo(12.0f, 18.0f)
                close()
                moveToRelative(0.0f, -2.0f)
                quadToRelative(3.35f, 0.0f, 6.013f, -1.738f)
                reflectiveQuadTo(21.8f, 10.0f)
                quadToRelative(-1.125f, -2.612f, -3.787f, -4.306f)
                reflectiveQuadTo(12.0f, 4.0f)
                reflectiveQuadTo(5.987f, 5.694f)
                reflectiveQuadTo(2.2f, 10.0f)
                quadToRelative(1.125f, 2.612f, 3.787f, 4.306f)
                reflectiveQuadTo(12.0f, 16.0f)
                close()
                moveToRelative(0.0f, -2.0f)
                quadToRelative(-1.675f, 0.0f, -2.837f, -1.163f)
                reflectiveQuadTo(8.0f, 10.0f)
                reflectiveQuadToRelative(1.163f, -2.837f)
                reflectiveQuadTo(12.0f, 6.0f)
                reflectiveQuadToRelative(2.838f, 1.163f)
                reflectiveQuadTo(16.0f, 10.0f)
                reflectiveQuadToRelative(-1.162f, 2.838f)
                reflectiveQuadTo(12.0f, 14.0f)
                close()
                moveToRelative(0.0f, -2.0f)
                quadToRelative(0.825f, 0.0f, 1.413f, -0.587f)
                reflectiveQuadTo(14.0f, 10.0f)
                reflectiveQuadToRelative(-0.587f, -1.413f)
                reflectiveQuadTo(12.0f, 8.0f)
                reflectiveQuadToRelative(-1.412f, 0.587f)
                reflectiveQuadTo(10.0f, 10.0f)
                reflectiveQuadToRelative(0.588f, 1.413f)
                reflectiveQuadTo(12.0f, 12.0f)
                close()
            }
        }.build()
        return _visibility!!
    }
