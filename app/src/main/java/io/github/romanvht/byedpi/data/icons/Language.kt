package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _language: ImageVector? = null

val IconsData.Language: ImageVector
    get() {
        if (_language != null) return _language!!
        _language = ImageVector.Builder(
            name = "Language",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.0f, 22.0f)
                quadToRelative(-2.075f, 0.0f, -3.9f, -0.788f)
                quadToRelative(-1.825f, -0.787f, -3.175f, -2.137f)
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
                quadToRelative(0.0f, -3.35f, -2.325f, -5.675f)
                reflectiveQuadTo(12.0f, 4.0f)
                quadToRelative(-3.35f, 0.0f, -5.675f, 2.325f)
                reflectiveQuadTo(4.0f, 12.0f)
                quadToRelative(0.0f, 3.35f, 2.325f, 5.675f)
                reflectiveQuadTo(12.0f, 20.0f)
                close()
                moveTo(12.0f, 18.0f)
                verticalLineTo(6.0f)
                quadToRelative(-2.25f, 0.0f, -3.625f, 2.325f)
                reflectiveQuadTo(7.0f, 12.0f)
                reflectiveQuadToRelative(1.375f, 3.675f)
                reflectiveQuadTo(12.0f, 18.0f)
                close()
                moveTo(12.0f, 18.0f)
                quadToRelative(2.25f, 0.0f, 3.625f, -2.325f)
                reflectiveQuadTo(17.0f, 12.0f)
                reflectiveQuadToRelative(-1.375f, -3.675f)
                reflectiveQuadTo(12.0f, 6.0f)
                close()
                moveTo(4.5f, 14.5f)
                horizontalLineToRelative(15.0f)
                verticalLineToRelative(-1.0f)
                horizontalLineToRelative(-15.0f)
                close()
                moveTo(4.5f, 10.5f)
                horizontalLineToRelative(15.0f)
                verticalLineToRelative(-1.0f)
                horizontalLineToRelative(-15.0f)
                close()
            }
        }.build()
        return _language!!
    }
