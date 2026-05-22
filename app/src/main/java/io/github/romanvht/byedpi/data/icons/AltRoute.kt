package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.AltRoute: ImageVector
  get() {
    if (_AltRoute != null) {
      return _AltRoute!!
    }
    _AltRoute =
      ImageVector.Builder(
          name = "AltRoute",
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
            moveTo(11f, 22f)
            verticalLineTo(17f)
            quadToRelative(0f, -1.4f, -0.42f, -2.08f)
            reflectiveQuadTo(9.45f, 13.6f)
            lineToRelative(1.42f, -1.43f)
            quadToRelative(0.3f, 0.28f, 0.58f, 0.59f)
            reflectiveQuadTo(12f, 13.43f)
            quadToRelative(0.35f, -0.47f, 0.71f, -0.84f)
            quadToRelative(0.36f, -0.36f, 0.74f, -0.71f)
            quadTo(14.4f, 11f, 15.18f, 9.85f)
            reflectiveQuadTo(16f, 5.82f)
            lineTo(14.43f, 7.4f)
            lineTo(13f, 6f)
            lineTo(17f, 2f)
            lineToRelative(4f, 4f)
            lineTo(19.6f, 7.4f)
            lineTo(18f, 5.82f)
            quadTo(17.95f, 9.4f, 16.9f, 10.91f)
            reflectiveQuadToRelative(-2.1f, 2.46f)
            quadTo(14f, 14.1f, 13.5f, 14.79f)
            reflectiveQuadTo(13f, 17f)
            verticalLineToRelative(5f)
            horizontalLineTo(11f)
            close()
            moveTo(6.2f, 8.17f)
            quadTo(6.1f, 7.68f, 6.06f, 7.07f)
            reflectiveQuadTo(6f, 5.82f)
            lineTo(4.4f, 7.4f)
            lineTo(3f, 6f)
            lineTo(7f, 2f)
            lineToRelative(4f, 4f)
            lineTo(9.58f, 7.4f)
            lineTo(8f, 5.85f)
            quadTo(8f, 6.38f, 8.05f, 6.84f)
            reflectiveQuadTo(8.15f, 7.7f)
            lineTo(6.2f, 8.17f)
            close()
            moveToRelative(2.15f, 4.4f)
            quadTo(7.85f, 12.05f, 7.39f, 11.35f)
            reflectiveQuadTo(6.58f, 9.63f)
            lineTo(8.5f, 9.15f)
            quadTo(8.75f, 9.82f, 9.08f, 10.3f)
            reflectiveQuadToRelative(0.7f, 0.85f)
            lineTo(8.35f, 12.58f)
            close()
          }
        }
        .build()
    return _AltRoute!!
  }

private var _AltRoute: ImageVector? = null
