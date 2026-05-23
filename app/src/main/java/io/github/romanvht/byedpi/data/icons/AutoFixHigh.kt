package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.AutoFixHigh: ImageVector
  get() {
    if (_AutoFixHigh != null) {
      return _AutoFixHigh!!
    }
    _AutoFixHigh =
      ImageVector.Builder(
          name = "AutoFixHigh",
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
            moveTo(20f, 7f)
            lineTo(19.05f, 4.95f)
            lineTo(17f, 4f)
            lineTo(19.05f, 3.05f)
            lineTo(20f, 1f)
            lineToRelative(0.95f, 2.05f)
            lineTo(23f, 4f)
            lineTo(20.95f, 4.95f)
            lineTo(20f, 7f)
            close()
            moveTo(8.5f, 7f)
            lineTo(7.55f, 4.95f)
            lineTo(5.5f, 4f)
            lineTo(7.55f, 3.05f)
            lineTo(8.5f, 1f)
            lineTo(9.45f, 3.05f)
            lineTo(11.5f, 4f)
            lineTo(9.45f, 4.95f)
            lineTo(8.5f, 7f)
            close()
            moveTo(20f, 18.5f)
            lineTo(19.05f, 16.45f)
            lineTo(17f, 15.5f)
            lineToRelative(2.05f, -0.95f)
            lineTo(20f, 12.5f)
            lineToRelative(0.95f, 2.05f)
            lineTo(23f, 15.5f)
            lineToRelative(-2.05f, 0.95f)
            lineTo(20f, 18.5f)
            close()
            moveTo(5.1f, 21.7f)
            lineTo(2.3f, 18.9f)
            quadTo(2f, 18.6f, 2f, 18.18f)
            reflectiveQuadTo(2.3f, 17.45f)
            lineTo(13.45f, 6.3f)
            quadTo(13.75f, 6f, 14.18f, 6f)
            reflectiveQuadTo(14.9f, 6.3f)
            lineToRelative(2.8f, 2.8f)
            quadTo(18f, 9.4f, 18f, 9.82f)
            reflectiveQuadToRelative(-0.3f, 0.72f)
            lineTo(6.55f, 21.7f)
            quadTo(6.25f, 22f, 5.83f, 22f)
            reflectiveQuadTo(5.1f, 21.7f)
            close()
            moveTo(14.18f, 11.23f)
            lineToRelative(1.4f, -1.4f)
            lineToRelative(-1.4f, -1.4f)
            lineToRelative(-1.4f, 1.4f)
            lineToRelative(1.4f, 1.4f)
            close()
          }
        }
        .build()
    return _AutoFixHigh!!
  }

private var _AutoFixHigh: ImageVector? = null
