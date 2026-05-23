package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.BatteryAlert: ImageVector
  get() {
    if (_BatteryAlert != null) {
      return _BatteryAlert!!
    }
    _BatteryAlert =
      ImageVector.Builder(
          name = "BatteryAlert",
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
            moveTo(11f, 14f)
            horizontalLineToRelative(2f)
            verticalLineTo(8f)
            horizontalLineTo(11f)
            verticalLineToRelative(6f)
            close()
            moveToRelative(1f, 4f)
            quadToRelative(0.43f, 0f, 0.71f, -0.29f)
            quadTo(13f, 17.43f, 13f, 17f)
            reflectiveQuadTo(12.71f, 16.29f)
            reflectiveQuadTo(12f, 16f)
            reflectiveQuadToRelative(-0.71f, 0.29f)
            reflectiveQuadTo(11f, 17f)
            reflectiveQuadToRelative(0.29f, 0.71f)
            reflectiveQuadTo(12f, 18f)
            close()
            moveTo(8f, 22f)
            quadTo(7.58f, 22f, 7.29f, 21.71f)
            quadTo(7f, 21.43f, 7f, 21f)
            verticalLineTo(5f)
            quadTo(7f, 4.57f, 7.29f, 4.29f)
            reflectiveQuadTo(8f, 4f)
            horizontalLineToRelative(2f)
            verticalLineTo(2f)
            horizontalLineToRelative(4f)
            verticalLineTo(4f)
            horizontalLineToRelative(2f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(17f, 5f)
            verticalLineTo(21f)
            quadToRelative(0f, 0.43f, -0.29f, 0.71f)
            reflectiveQuadTo(16f, 22f)
            horizontalLineTo(8f)
            close()
          }
        }
        .build()
    return _BatteryAlert!!
  }

private var _BatteryAlert: ImageVector? = null
