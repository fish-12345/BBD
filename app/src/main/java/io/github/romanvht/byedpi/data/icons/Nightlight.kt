package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.Nightlight: ImageVector
  get() {
    if (_Nightlight != null) {
      return _Nightlight!!
    }
    _Nightlight =
      ImageVector.Builder(
          name = "Nightlight",
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
            moveTo(14f, 22f)
            quadToRelative(-2.05f, 0f, -3.88f, -0.79f)
            quadTo(8.3f, 20.43f, 6.94f, 19.06f)
            quadTo(5.58f, 17.7f, 4.79f, 15.88f)
            reflectiveQuadTo(4f, 11.99f)
            reflectiveQuadTo(4.79f, 8.11f)
            reflectiveQuadTo(6.94f, 4.94f)
            reflectiveQuadTo(10.13f, 2.79f)
            reflectiveQuadTo(14f, 2f)
            quadToRelative(1.35f, 0f, 2.63f, 0.35f)
            reflectiveQuadToRelative(2.38f, 1f)
            quadTo(16.73f, 4.67f, 15.36f, 6.94f)
            reflectiveQuadTo(14f, 12f)
            reflectiveQuadToRelative(1.36f, 5.06f)
            reflectiveQuadTo(19f, 20.65f)
            quadToRelative(-1.1f, 0.65f, -2.38f, 1f)
            reflectiveQuadTo(14f, 22f)
            close()
          }
        }
        .build()
    return _Nightlight!!
  }

private var _Nightlight: ImageVector? = null
