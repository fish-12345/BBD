package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _block: ImageVector? = null

val IconsData.Block: ImageVector
    get() {
        if (_block != null) return _block!!
        _block = ImageVector.Builder(
            name = "Block",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 22.0f)
                quadToRelative(-2.075f, 0.0f, -3.9f, -0.788f)
                reflectiveQuadToRelative(-3.175f, -2.137f)
                quadToRelative(-1.35f, -1.35f, -2.137f, -3.175f)
                reflectiveQuadTo(2.0f, 12.0f)
                quadToRelative(0.0f, -2.075f, 0.788f, -3.9f)
                reflectiveQuadToRelative(2.137f, -3.175f)
                quadToRelative(1.35f, -1.35f, 3.175f, -2.137f)
                reflectiveQuadTo(12.0f, 2.0f)
                quadToRelative(2.075f, 0.0f, 3.9f, 0.788f)
                reflectiveQuadToRelative(3.175f, 2.137f)
                quadToRelative(1.35f, 1.35f, 2.138f, 3.175f)
                reflectiveQuadTo(22.0f, 12.0f)
                quadToRelative(0.0f, 2.075f, -0.788f, 3.9f)
                reflectiveQuadToRelative(-2.137f, 3.175f)
                quadToRelative(-1.35f, 1.35f, -3.175f, 2.138f)
                reflectiveQuadTo(12.0f, 22.0f)
                close()
                moveTo(12.0f, 20.0f)
                quadToRelative(3.35f, 0.0f, 5.675f, -2.325f)
                reflectiveQuadTo(20.0f, 12.0f)
                quadToRelative(0.0f, -1.4f, -0.475f, -2.663f)
                reflectiveQuadTo(18.2f, 7.1f)
                lineTo(7.1f, 18.2f)
                quadToRelative(1.075f, 0.85f, 2.338f, 1.325f)
                reflectiveQuadTo(12.0f, 20.0f)
                close()
                moveTo(5.8f, 17.1f)
                lineTo(16.9f, 5.8f)
                quadToRelative(-1.075f, -0.85f, -2.337f, -1.325f)
                reflectiveQuadTo(12.0f, 4.0f)
                quadToRelative(-3.35f, 0.0f, -5.675f, 2.325f)
                reflectiveQuadTo(4.0f, 12.0f)
                quadToRelative(0.0f, 1.4f, 0.475f, 2.663f)
                reflectiveQuadTo(5.8f, 17.1f)
                close()
            }
        }.build()
        return _block!!
    }
