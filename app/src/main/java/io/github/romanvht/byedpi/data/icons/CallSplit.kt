package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.CallSplit: ImageVector
  get() {
    if (_CallSplit != null) {
      return _CallSplit!!
    }
    _CallSplit =
      ImageVector.Builder(
          name = "CallSplit",
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
            moveTo(11f, 20f)
            verticalLineTo(12.4f)
            lineTo(6f, 7.4f)
            verticalLineTo(10f)
            horizontalLineTo(4f)
            verticalLineTo(4f)
            horizontalLineToRelative(6f)
            verticalLineTo(6f)
            horizontalLineTo(7.4f)
            lineTo(13f, 11.6f)
            verticalLineTo(20f)
            horizontalLineTo(11f)
            close()
            moveToRelative(3.85f, -9.4f)
            lineTo(13.4f, 9.15f)
            lineTo(16.6f, 6f)
            horizontalLineTo(14f)
            verticalLineTo(4f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(6f)
            horizontalLineTo(18f)
            verticalLineTo(7.4f)
            lineToRelative(-3.15f, 3.2f)
            close()
          }
        }
        .build()
    return _CallSplit!!
  }

private var _CallSplit: ImageVector? = null
