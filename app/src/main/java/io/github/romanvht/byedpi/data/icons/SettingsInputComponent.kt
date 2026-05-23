package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.SettingsInputComponent: ImageVector
  get() {
    if (_SettingsInputComponent != null) {
      return _SettingsInputComponent!!
    }
    _SettingsInputComponent =
      ImageVector.Builder(
          name = "SettingsInputComponent",
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
            moveTo(3f, 23f)
            verticalLineTo(18.8f)
            quadTo(2.13f, 18.5f, 1.56f, 17.74f)
            reflectiveQuadTo(1f, 16f)
            verticalLineTo(14f)
            horizontalLineTo(7f)
            verticalLineToRelative(2f)
            quadToRelative(0f, 0.98f, -0.56f, 1.74f)
            reflectiveQuadTo(5f, 18.8f)
            verticalLineTo(23f)
            horizontalLineTo(3f)
            close()
            moveToRelative(8f, 0f)
            verticalLineTo(18.8f)
            quadTo(10.13f, 18.5f, 9.56f, 17.74f)
            reflectiveQuadTo(9f, 16f)
            verticalLineTo(14f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            quadToRelative(0f, 0.98f, -0.56f, 1.74f)
            reflectiveQuadTo(13f, 18.8f)
            verticalLineTo(23f)
            horizontalLineTo(11f)
            close()
            moveToRelative(8f, 0f)
            verticalLineTo(18.8f)
            quadTo(18.13f, 18.5f, 17.56f, 17.74f)
            reflectiveQuadTo(17f, 16f)
            verticalLineTo(14f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            quadToRelative(0f, 0.98f, -0.56f, 1.74f)
            reflectiveQuadTo(21f, 18.8f)
            verticalLineTo(23f)
            horizontalLineTo(19f)
            close()
            moveTo(1f, 12f)
            verticalLineTo(6f)
            horizontalLineTo(3f)
            verticalLineTo(2f)
            quadTo(3f, 1.57f, 3.29f, 1.29f)
            reflectiveQuadTo(4f, 1f)
            reflectiveQuadTo(4.71f, 1.29f)
            reflectiveQuadTo(5f, 2f)
            verticalLineTo(6f)
            horizontalLineTo(7f)
            verticalLineToRelative(6f)
            horizontalLineTo(1f)
            close()
            moveToRelative(8f, 0f)
            verticalLineTo(6f)
            horizontalLineToRelative(2f)
            verticalLineTo(2f)
            quadTo(11f, 1.57f, 11.29f, 1.29f)
            reflectiveQuadTo(12f, 1f)
            reflectiveQuadToRelative(0.71f, 0.29f)
            reflectiveQuadTo(13f, 2f)
            verticalLineTo(6f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            horizontalLineTo(9f)
            close()
            moveToRelative(8f, 0f)
            verticalLineTo(6f)
            horizontalLineToRelative(2f)
            verticalLineTo(2f)
            quadTo(19f, 1.57f, 19.29f, 1.29f)
            reflectiveQuadTo(20f, 1f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(21f, 2f)
            verticalLineTo(6f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(6f)
            horizontalLineTo(17f)
            close()
          }
        }
        .build()
    return _SettingsInputComponent!!
  }

private var _SettingsInputComponent: ImageVector? = null
