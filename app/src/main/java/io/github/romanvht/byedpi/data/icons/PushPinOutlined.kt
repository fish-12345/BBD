package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.PushPinOutlined: ImageVector
    get() {
        if (_PushPinOutlined != null) {
            return _PushPinOutlined!!
        }
        _PushPinOutlined =
            ImageVector.Builder(
                name = "PushPinOutlined",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            )
                .apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Bevel,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(16f, 12f)
                        lineToRelative(2f, 2f)
                        verticalLineToRelative(2f)
                        horizontalLineTo(13f)
                        verticalLineToRelative(6f)
                        lineToRelative(-1f, 1f)
                        lineTo(11f, 22f)
                        verticalLineTo(16f)
                        horizontalLineTo(6f)
                        verticalLineTo(14f)
                        lineTo(8f, 12f)
                        verticalLineTo(5f)
                        horizontalLineTo(7f)
                        verticalLineTo(3f)
                        horizontalLineTo(17f)
                        verticalLineTo(5f)
                        horizontalLineTo(16f)
                        verticalLineToRelative(7f)
                        close()
                        moveTo(8.85f, 14f)
                        horizontalLineToRelative(6.3f)
                        lineTo(14f, 12.85f)
                        verticalLineTo(5f)
                        horizontalLineTo(10f)
                        verticalLineToRelative(7.85f)
                        lineTo(8.85f, 14f)
                        close()
                        moveTo(12f, 14f)
                        close()
                    }
                }
                .build()
        return _PushPinOutlined!!
    }

private var _PushPinOutlined: ImageVector? = null