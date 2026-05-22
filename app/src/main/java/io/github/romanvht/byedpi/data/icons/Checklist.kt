package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.Checklist: ImageVector
  get() {
    if (_Checklist != null) {
      return _Checklist!!
    }
    _Checklist =
      ImageVector.Builder(
          name = "Checklist",
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
            moveTo(5.55f, 19f)
            lineTo(2f, 15.45f)
            lineToRelative(1.4f, -1.4f)
            lineToRelative(2.13f, 2.13f)
            lineTo(9.78f, 11.93f)
            lineToRelative(1.4f, 1.43f)
            lineTo(5.55f, 19f)
            close()
            moveToRelative(0f, -8f)
            lineTo(2f, 7.45f)
            lineTo(3.4f, 6.05f)
            lineTo(5.53f, 8.17f)
            lineTo(9.78f, 3.92f)
            lineToRelative(1.4f, 1.43f)
            lineTo(5.55f, 11f)
            close()
            moveTo(13f, 17f)
            verticalLineTo(15f)
            horizontalLineToRelative(9f)
            verticalLineToRelative(2f)
            horizontalLineTo(13f)
            close()
            moveTo(13f, 9f)
            verticalLineTo(7f)
            horizontalLineToRelative(9f)
            verticalLineTo(9f)
            horizontalLineTo(13f)
            close()
          }
        }
        .build()
    return _Checklist!!
  }

private var _Checklist: ImageVector? = null
