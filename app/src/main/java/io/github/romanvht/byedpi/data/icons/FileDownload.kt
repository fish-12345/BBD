package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _fileDownload: ImageVector? = null

val IconsData.FileDownload: ImageVector
    get() {
        if (_fileDownload != null) return _fileDownload!!
        _fileDownload = ImageVector.Builder(
            name = "FileDownload",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 16.0f)
            lineTo(7.0f, 11.0f)
            lineTo(8.4f, 9.55f)
            lineTo(11.0f, 12.15f)
            verticalLineTo(4.0f)
            horizontalLineTo(13.0f)
            verticalLineTo(12.15f)
            lineTo(15.6f, 9.55f)
            lineTo(17.0f, 11.0f)
            lineTo(12.0f, 16.0f)
            close()
            moveTo(4.0f, 20.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(16.0f)
            verticalLineToRelative(2.0f)
            horizontalLineTo(4.0f)
            close()
        }.build()
        return _fileDownload!!
    }
