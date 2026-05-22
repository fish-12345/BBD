package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.SignalCellularAlt: ImageVector
  get() {
    if (_SignalCellularAlt != null) {
      return _SignalCellularAlt!!
    }
    _SignalCellularAlt =
      ImageVector.Builder(
          name = "SignalCellularAlt",
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
            moveTo(5f, 20f)
            verticalLineTo(14f)
            horizontalLineTo(8f)
            verticalLineToRelative(6f)
            horizontalLineTo(5f)
            close()
            moveToRelative(6f, 0f)
            verticalLineTo(9f)
            horizontalLineToRelative(3f)
            verticalLineTo(20f)
            horizontalLineTo(11f)
            close()
            moveToRelative(6f, 0f)
            verticalLineTo(4f)
            horizontalLineToRelative(3f)
            verticalLineTo(20f)
            horizontalLineTo(17f)
            close()
          }
        }
        .build()
    return _SignalCellularAlt!!
  }

private var _SignalCellularAlt: ImageVector? = null
