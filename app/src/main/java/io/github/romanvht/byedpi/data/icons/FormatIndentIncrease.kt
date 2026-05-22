package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _formatIndentIncrease: ImageVector? = null

val IconsData.FormatIndentIncrease: ImageVector
    get() {
        if (_formatIndentIncrease != null) return _formatIndentIncrease!!
        _formatIndentIncrease = ImageVector.Builder(
            name = "FormatIndentIncrease",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(3f, 21f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(18f)
                verticalLineToRelative(2f)
                close()
                moveTo(11f, 17f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(10f)
                verticalLineToRelative(2f)
                close()
                moveTo(11f, 13f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(10f)
                verticalLineToRelative(2f)
                close()
                moveTo(11f, 9f)
                verticalLineTo(7f)
                horizontalLineToRelative(10f)
                verticalLineToRelative(2f)
                close()
                moveTo(3f, 5f)
                verticalLineTo(3f)
                horizontalLineToRelative(18f)
                verticalLineToRelative(2f)
                close()
                moveTo(3f, 16f)
                verticalLineTo(8f)
                lineToRelative(4f, 4f)
                close()
            }
        }.build()
        return _formatIndentIncrease!!
    }
