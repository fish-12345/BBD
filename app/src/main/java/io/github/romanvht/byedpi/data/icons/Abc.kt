package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _abc: ImageVector? = null

val IconsData.Abc: ImageVector
    get() {
        if (_abc != null) return _abc!!
        _abc = ImageVector.Builder(
            name = "Abc",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(212f, 640f)
                quadToRelative(-30f, 0f, -51f, -21f)
                reflectiveQuadToRelative(-21f, -51f)
                verticalLineToRelative(-160f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(220f, 328f)
                horizontalLineToRelative(120f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(420f, 408f)
                verticalLineToRelative(160f)
                quadToRelative(0f, 30f, -21f, 51f)
                reflectiveQuadToRelative(-51f, 21f)
                lineTo(212f, 640f)
                close()
                moveTo(220f, 560f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(-152f)
                lineTo(220f, 408f)
                verticalLineToRelative(152f)
                close()
                moveTo(460f, 640f)
                verticalLineToRelative(-312f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(82f)
                quadToRelative(18f, -41f, 52.5f, -62.5f)
                reflectiveQuadTo(660f, 326f)
                quadToRelative(41f, 0f, 70.5f, 21.5f)
                reflectiveQuadTo(774f, 404f)
                quadToRelative(13f, -33f, 43f, -54.5f)
                reflectiveQuadTo(884f, 328f)
                quadToRelative(46f, 0f, 71f, 29.5f)
                reflectiveQuadToRelative(25f, 82.5f)
                verticalLineToRelative(200f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-200f)
                quadToRelative(0f, -24f, -12f, -32f)
                reflectiveQuadToRelative(-28f, -8f)
                quadToRelative(-20f, 0f, -33f, 13.5f)
                reflectiveQuadTo(812f, 448f)
                verticalLineToRelative(192f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-200f)
                quadToRelative(0f, -23f, -12.5f, -31.5f)
                reflectiveQuadTo(690f, 400f)
                quadToRelative(-20f, 0f, -33f, 13.5f)
                reflectiveQuadTo(642f, 448f)
                verticalLineToRelative(192f)
                horizontalLineToRelative(-182f)
                close()
            }
        }.build()
        return _abc!!
    }
