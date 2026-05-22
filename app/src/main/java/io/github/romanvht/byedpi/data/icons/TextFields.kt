package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.TextFields: ImageVector
  get() {
    if (_TextFields != null) {
      return _TextFields!!
    }
    _TextFields =
      ImageVector.Builder(
          name = "TextFields",
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
            moveTo(7f, 20f)
            verticalLineTo(7f)
            horizontalLineTo(2f)
            verticalLineTo(4f)
            horizontalLineTo(15f)
            verticalLineTo(7f)
            horizontalLineTo(10f)
            verticalLineTo(20f)
            horizontalLineTo(7f)
            close()
            moveToRelative(9f, 0f)
            verticalLineTo(12f)
            horizontalLineTo(13f)
            verticalLineTo(9f)
            horizontalLineToRelative(9f)
            verticalLineToRelative(3f)
            horizontalLineTo(19f)
            verticalLineToRelative(8f)
            horizontalLineTo(16f)
            close()
          }
        }
        .build()
    return _TextFields!!
  }

private var _TextFields: ImageVector? = null
