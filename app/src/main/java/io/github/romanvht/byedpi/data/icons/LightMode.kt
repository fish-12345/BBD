package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.LightMode: ImageVector
  get() {
    if (_LightMode != null) {
      return _LightMode!!
    }
    _LightMode =
      ImageVector.Builder(
          name = "LightMode",
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
            moveTo(8.46f, 15.54f)
            quadTo(7f, 14.08f, 7f, 12f)
            quadTo(7f, 9.92f, 8.46f, 8.46f)
            reflectiveQuadTo(12f, 7f)
            reflectiveQuadToRelative(3.54f, 1.46f)
            reflectiveQuadTo(17f, 12f)
            reflectiveQuadToRelative(-1.46f, 3.54f)
            reflectiveQuadTo(12f, 17f)
            quadTo(9.93f, 17f, 8.46f, 15.54f)
            close()
            moveTo(5f, 13f)
            horizontalLineTo(1f)
            verticalLineTo(11f)
            horizontalLineTo(5f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(18f, 0f)
            horizontalLineTo(19f)
            verticalLineTo(11f)
            horizontalLineToRelative(4f)
            verticalLineToRelative(2f)
            close()
            moveTo(11f, 5f)
            verticalLineTo(1f)
            horizontalLineToRelative(2f)
            verticalLineTo(5f)
            horizontalLineTo(11f)
            close()
            moveToRelative(0f, 18f)
            verticalLineTo(19f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(4f)
            horizontalLineTo(11f)
            close()
            moveTo(6.4f, 7.75f)
            lineTo(3.88f, 5.32f)
            lineTo(5.3f, 3.85f)
            lineToRelative(2.4f, 2.5f)
            lineTo(6.4f, 7.75f)
            close()
            moveToRelative(12.3f, 12.4f)
            lineTo(16.28f, 17.63f)
            lineTo(17.6f, 16.25f)
            lineToRelative(2.52f, 2.43f)
            lineTo(18.7f, 20.15f)
            close()
            moveTo(16.25f, 6.4f)
            lineTo(18.68f, 3.88f)
            lineTo(20.15f, 5.3f)
            lineToRelative(-2.5f, 2.4f)
            lineTo(16.25f, 6.4f)
            close()
            moveTo(3.85f, 18.7f)
            lineTo(6.38f, 16.27f)
            lineTo(7.75f, 17.6f)
            lineTo(5.33f, 20.13f)
            lineTo(3.85f, 18.7f)
            close()
          }
        }
        .build()
    return _LightMode!!
  }

private var _LightMode: ImageVector? = null
