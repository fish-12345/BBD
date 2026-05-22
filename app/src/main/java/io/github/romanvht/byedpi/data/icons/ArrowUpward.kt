package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _arrowUpward: ImageVector? = null

val IconsData.ArrowUpward: ImageVector
    get() {
        if (_arrowUpward != null) return _arrowUpward!!
        _arrowUpward = ImageVector.Builder(
            name = "arrow_upward",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11f, 20f)
                verticalLineTo(7.82f)
                lineToRelative(-5.6f, 5.6f)
                lineTo(4f, 12f)
                lineTo(12f, 4f)
                lineToRelative(8f, 8f)
                lineToRelative(-1.4f, 1.42f)
                lineTo(13f, 7.82f)
                verticalLineTo(20f)
                horizontalLineTo(11f)
                close()
            }
        }.build()
        return _arrowUpward!!
    }
