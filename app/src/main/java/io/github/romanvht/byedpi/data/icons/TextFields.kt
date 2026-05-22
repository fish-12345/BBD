package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _textFields: ImageVector? = null

val IconsData.TextFields: ImageVector
    get() {
        if (_textFields != null) return _textFields!!
        _textFields = ImageVector.Builder(
            name = "TextFields",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(2.5f, 4.0f)
            verticalLineToRelative(3.0f)
            horizontalLineToRelative(5.0f)
            verticalLineToRelative(12.0f)
            horizontalLineToRelative(3.0f)
            verticalLineTo(7.0f)
            horizontalLineToRelative(5.0f)
            verticalLineTo(4.0f)
            horizontalLineTo(2.5f)
            close()
            moveTo(21.5f, 9.0f)
            horizontalLineToRelative(-9.0f)
            verticalLineToRelative(3.0f)
            horizontalLineToRelative(3.0f)
            verticalLineToRelative(7.0f)
            horizontalLineToRelative(3.0f)
            verticalLineToRelative(-7.0f)
            horizontalLineToRelative(3.0f)
            verticalLineTo(9.0f)
            close()
        }.build()
        return _textFields!!
    }
