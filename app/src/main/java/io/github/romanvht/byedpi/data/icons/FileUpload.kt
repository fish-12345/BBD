package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _fileUpload: ImageVector? = null

val IconsData.FileUpload: ImageVector
    get() {
        if (_fileUpload != null) return _fileUpload!!
        _fileUpload = ImageVector.Builder(
            name = "FileUpload",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(11.0f, 16.0f)
            verticalLineTo(7.85f)
            lineTo(8.4f, 10.45f)
            lineTo(7.0f, 9.0f)
            lineToRelative(5.0f, -5.0f)
            lineToRelative(5.0f, 5.0f)
            lineToRelative(-1.4f, 1.45f)
            lineToRelative(-2.6f, -2.6f)
            verticalLineTo(16.0f)
            horizontalLineTo(11.0f)
            close()
            moveTo(4.0f, 20.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(16.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(4.0f)
            close()
        }.build()
        return _fileUpload!!
    }
