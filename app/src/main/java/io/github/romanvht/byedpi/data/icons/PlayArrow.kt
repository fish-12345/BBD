package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.PlayArrow: ImageVector
  get() {
    if (_PlayArrow != null) {
      return _PlayArrow!!
    }
    _PlayArrow =
      ImageVector.Builder(
          name = "PlayArrow",
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
            moveTo(8f, 19f)
            verticalLineTo(5f)
            lineToRelative(11f, 7f)
            lineTo(8f, 19f)
            close()
          }
        }
        .build()
    return _PlayArrow!!
  }

private var _PlayArrow: ImageVector? = null
