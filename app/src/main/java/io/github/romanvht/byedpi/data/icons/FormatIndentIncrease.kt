package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.FormatIndentIncrease: ImageVector
  get() {
    if (_FormatIndentIncrease != null) {
      return _FormatIndentIncrease!!
    }
    _FormatIndentIncrease =
      ImageVector.Builder(
          name = "FormatIndentIncrease",
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
            moveTo(3f, 21f)
            verticalLineTo(19f)
            horizontalLineTo(21f)
            verticalLineToRelative(2f)
            horizontalLineTo(3f)
            close()
            moveToRelative(8f, -4f)
            verticalLineTo(15f)
            horizontalLineTo(21f)
            verticalLineToRelative(2f)
            horizontalLineTo(11f)
            close()
            moveToRelative(0f, -4f)
            verticalLineTo(11f)
            horizontalLineTo(21f)
            verticalLineToRelative(2f)
            horizontalLineTo(11f)
            close()
            moveTo(11f, 9f)
            verticalLineTo(7f)
            horizontalLineTo(21f)
            verticalLineTo(9f)
            horizontalLineTo(11f)
            close()
            moveTo(3f, 5f)
            verticalLineTo(3f)
            horizontalLineTo(21f)
            verticalLineTo(5f)
            horizontalLineTo(3f)
            close()
            moveTo(3f, 16f)
            verticalLineTo(8f)
            lineToRelative(4f, 4f)
            lineTo(3f, 16f)
            close()
          }
        }
        .build()
    return _FormatIndentIncrease!!
  }

private var _FormatIndentIncrease: ImageVector? = null
