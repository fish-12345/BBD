package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _callSplit: ImageVector? = null

val IconsData.CallSplit: ImageVector
    get() {
        if (_callSplit != null) return _callSplit!!
        _callSplit = ImageVector.Builder(
            name = "CallSplit",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.0f, 4.0f)
                lineToRelative(2.29f, 2.29f)
                lineToRelative(-2.88f, 2.88f)
                lineToRelative(1.42f, 1.42f)
                lineToRelative(2.88f, -2.88f)
                lineTo(20.0f, 10.0f)
                verticalLineTo(4.0f)
                close()
                moveTo(10.0f, 4.0f)
                horizontalLineTo(4.0f)
                verticalLineToRelative(6.0f)
                lineToRelative(2.29f, -2.29f)
                lineTo(9.0f, 10.41f)
                verticalLineTo(15.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(9.59f)
                lineTo(8.29f, 6.88f)
                close()
                moveTo(13.0f, 20.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(2.0f)
                close()
            }
        }.build()
        return _callSplit!!
    }
