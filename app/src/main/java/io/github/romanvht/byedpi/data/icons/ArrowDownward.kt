package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _arrowDownward: ImageVector? = null

val IconsData.ArrowDownward: ImageVector
    get() {
        if (_arrowDownward != null) return _arrowDownward!!
        _arrowDownward = ImageVector.Builder(
            name = "arrow_downward",
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
                moveTo(11f, 4f)
                verticalLineTo(16.18f)
                lineTo(5.4f, 10.58f)
                lineTo(4f, 12f)
                lineToRelative(8f, 8f)
                lineToRelative(8f, -8f)
                lineTo(18.6f, 10.58f)
                lineTo(13f, 16.18f)
                verticalLineTo(4f)
                horizontalLineTo(11f)
                close()
            }
        }.build()
        return _arrowDownward!!
    }
