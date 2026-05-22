package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.DataArray: ImageVector
  get() {
    if (_DataArray != null) {
      return _DataArray!!
    }
    _DataArray =
      ImageVector.Builder(
          name = "DataArray",
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
            moveTo(15f, 20f)
            verticalLineTo(18f)
            horizontalLineToRelative(3f)
            verticalLineTo(6f)
            horizontalLineTo(15f)
            verticalLineTo(4f)
            horizontalLineToRelative(5f)
            verticalLineTo(20f)
            horizontalLineTo(15f)
            close()
            moveTo(4f, 20f)
            verticalLineTo(4f)
            horizontalLineTo(9f)
            verticalLineTo(6f)
            horizontalLineTo(6f)
            verticalLineTo(18f)
            horizontalLineTo(9f)
            verticalLineToRelative(2f)
            horizontalLineTo(4f)
            close()
          }
        }
        .build()
    return _DataArray!!
  }

private var _DataArray: ImageVector? = null
