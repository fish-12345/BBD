package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _notes: ImageVector? = null

val IconsData.Notes: ImageVector
    get() {
        if (_notes != null) return _notes!!
        _notes = ImageVector.Builder(
            name = "Notes",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3.0f, 18.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(3.0f, 13.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(3.0f, 8.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(2.0f)
                close()
            }
        }.build()
        return _notes!!
    }
