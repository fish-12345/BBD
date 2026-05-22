package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.SpaceBar: ImageVector
  get() {
    if (_SpaceBar != null) {
      return _SpaceBar!!
    }
    _SpaceBar =
      ImageVector.Builder(
          name = "SpaceBar",
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
            moveTo(4f, 15f)
            verticalLineTo(9f)
            horizontalLineTo(6f)
            verticalLineToRelative(4f)
            horizontalLineTo(18f)
            verticalLineTo(9f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            horizontalLineTo(4f)
            close()
          }
        }
        .build()
    return _SpaceBar!!
  }

private var _SpaceBar: ImageVector? = null
