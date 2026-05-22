package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _colorLens: ImageVector? = null

val IconsData.ColorLens: ImageVector
    get() {
        if (_colorLens != null) return _colorLens!!
        _colorLens = ImageVector.Builder(
            name = "ColorLens",
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
                quadToRelative(-2.1f, 0.0f, -3.55f, -1.45f)
                reflectiveQuadTo(7.0f, 17.0f)
                quadToRelative(0.0f, -0.62f, 0.2f, -1.18f)
                reflectiveQuadToRelative(0.55f, -1.07f)
                lineToRelative(0.75f, -1.0f)
                quadToRelative(0.15f, -0.2f, 0.22f, -0.41f)
                reflectiveQuadToRelative(0.08f, -0.44f)
                quadToRelative(0.0f, -0.42f, -0.29f, -0.71f)
                reflectiveQuadTo(7.8f, 11.9f)
                horizontalLineTo(4.0f)
                quadToRelative(-0.82f, 0.0f, -1.41f, -0.59f)
                reflectiveQuadTo(2.0f, 9.9f)
                quadToRelative(0.0f, -3.3f, 2.35f, -5.6f)
                reflectiveQuadTo(10.0f, 2.0f)
                horizontalLineToRelative(2.0f)
                quadToRelative(4.15f, 0.0f, 7.07f, 2.93f)
                reflectiveQuadTo(22.0f, 12.0f)
                quadToRelative(0.0f, 4.15f, -2.93f, 7.07f)
                reflectiveQuadTo(12.0f, 22.0f)
                close()
                moveToRelative(0.0f, -2.0f)
                quadToRelative(3.32f, 0.0f, 5.66f, -2.34f)
                reflectiveQuadTo(20.0f, 12.0f)
                quadToRelative(0.0f, -3.32f, -2.34f, -5.66f)
                reflectiveQuadTo(12.0f, 4.0f)
                horizontalLineToRelative(-2.0f)
                quadToRelative(-2.48f, 0.0f, -4.24f, 1.76f)
                reflectiveQuadTo(4.0f, 10.0f)
                verticalLineToRelative(0.0f)
                horizontalLineToRelative(3.8f)
                quadToRelative(1.25f, 0.0f, 2.13f, 0.87f)
                reflectiveQuadToRelative(0.87f, 2.13f)
                quadToRelative(0.0f, 0.52f, -0.17f, 1.02f)
                reflectiveQuadToRelative(-0.53f, 0.93f)
                lineToRelative(-0.75f, 1.0f)
                quadToRelative(-0.25f, 0.35f, -0.35f, 0.72f)
                reflectiveQuadTo(9.0f, 17.0f)
                quadToRelative(0.0f, 1.25f, 0.88f, 2.13f)
                reflectiveQuadTo(12.0f, 20.0f)
                close()
                moveTo(6.5f, 9.0f)
                quadToRelative(0.62f, 0.0f, 1.06f, -0.44f)
                reflectiveQuadTo(8.0f, 7.5f)
                reflectiveQuadToRelative(-0.44f, -1.06f)
                reflectiveQuadTo(6.5f, 6.0f)
                reflectiveQuadToRelative(-1.06f, 0.44f)
                reflectiveQuadTo(5.0f, 7.5f)
                reflectiveQuadToRelative(0.44f, 1.06f)
                reflectiveQuadTo(6.5f, 9.0f)
                close()
                moveTo(10.0f, 6.0f)
                quadToRelative(0.62f, 0.0f, 1.06f, -0.44f)
                reflectiveQuadTo(11.5f, 4.5f)
                reflectiveQuadToRelative(-0.44f, -1.06f)
                reflectiveQuadTo(10.0f, 3.0f)
                reflectiveQuadToRelative(-1.06f, 0.44f)
                reflectiveQuadTo(8.5f, 4.5f)
                reflectiveQuadToRelative(0.44f, 1.06f)
                reflectiveQuadTo(10.0f, 6.0f)
                close()
                moveTo(14.5f, 9.0f)
                quadToRelative(0.62f, 0.0f, 1.06f, -0.44f)
                reflectiveQuadTo(16.0f, 7.5f)
                reflectiveQuadToRelative(-0.44f, -1.06f)
                reflectiveQuadTo(14.5f, 6.0f)
                reflectiveQuadToRelative(-1.06f, 0.44f)
                reflectiveQuadTo(13.0f, 7.5f)
                reflectiveQuadToRelative(0.44f, 1.06f)
                reflectiveQuadTo(14.5f, 9.0f)
                close()
                moveTo(18.0f, 13.0f)
                quadToRelative(0.62f, 0.0f, 1.06f, -0.44f)
                reflectiveQuadToRelative(0.44f, -1.06f)
                reflectiveQuadToRelative(-0.44f, -1.06f)
                reflectiveQuadTo(18.0f, 10.0f)
                reflectiveQuadToRelative(-1.06f, 0.44f)
                reflectiveQuadToRelative(-0.44f, 1.06f)
                reflectiveQuadToRelative(0.44f, 1.06f)
                reflectiveQuadTo(18.0f, 13.0f)
                close()
            }
        }.build()
        return _colorLens!!
    }
