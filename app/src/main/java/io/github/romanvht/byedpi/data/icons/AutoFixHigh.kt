package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _autoFixHigh: ImageVector? = null

val IconsData.AutoFixHigh: ImageVector
    get() {
        if (_autoFixHigh != null) return _autoFixHigh!!
        _autoFixHigh = ImageVector.Builder(
            name = "AutoFixHigh",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.5f, 5.6f)
                lineTo(10.0f, 7.0f)
                lineTo(8.6f, 4.5f)
                lineTo(10.0f, 2.0f)
                lineTo(7.5f, 3.4f)
                lineTo(5.0f, 2.0f)
                lineTo(6.4f, 4.5f)
                lineTo(5.0f, 7.0f)
                close()
                moveTo(19.5f, 9.6f)
                lineTo(22.0f, 11.0f)
                lineTo(20.6f, 8.5f)
                lineTo(22.0f, 6.0f)
                lineTo(19.5f, 7.4f)
                lineTo(17.0f, 6.0f)
                lineTo(18.4f, 8.5f)
                lineTo(17.0f, 11.0f)
                close()
                moveTo(4.5f, 11.6f)
                lineTo(7.0f, 13.0f)
                lineTo(5.6f, 10.5f)
                lineTo(7.0f, 8.0f)
                lineTo(4.5f, 9.4f)
                lineTo(2.0f, 8.0f)
                lineTo(3.4f, 10.5f)
                lineTo(2.0f, 13.0f)
                close()
                moveTo(14.06f, 6.19f)
                lineTo(5.14f, 15.11f)
                lineTo(8.89f, 18.86f)
                lineTo(17.81f, 9.94f)
                lineTo(14.06f, 6.19f)
                close()
                moveTo(17.61f, 2.74f)
                curveTo(18.1f, 2.25f, 18.89f, 2.25f, 19.38f, 2.74f)
                lineTo(21.26f, 4.62f)
                curveTo(21.75f, 5.11f, 21.75f, 5.9f, 21.26f, 6.39f)
                lineTo(19.16f, 8.49f)
                lineTo(15.51f, 4.84f)
                lineTo(17.61f, 2.74f)
                close()
            }
        }.build()
        return _autoFixHigh!!
    }
