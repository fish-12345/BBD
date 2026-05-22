package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.Abc: ImageVector
  get() {
    if (_Abc != null) {
      return _Abc!!
    }
    _Abc =
      ImageVector.Builder(
          name = "Abc",
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
            moveTo(17f, 15f)
            quadToRelative(-0.43f, 0f, -0.71f, -0.29f)
            reflectiveQuadTo(16f, 14f)
            verticalLineTo(10f)
            quadTo(16f, 9.57f, 16.29f, 9.29f)
            reflectiveQuadTo(17f, 9f)
            horizontalLineToRelative(3f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(21f, 10f)
            verticalLineToRelative(1f)
            horizontalLineTo(19.5f)
            verticalLineTo(10.5f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(2f)
            verticalLineTo(13f)
            horizontalLineTo(21f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.42f, -0.29f, 0.71f)
            reflectiveQuadTo(20f, 15f)
            horizontalLineTo(17f)
            close()
            moveTo(9.5f, 15f)
            verticalLineTo(9f)
            horizontalLineToRelative(4f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(14.5f, 10f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.42f, -0.29f, 0.71f)
            reflectiveQuadTo(13.5f, 12f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(14.5f, 13f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.42f, -0.29f, 0.71f)
            reflectiveQuadTo(13.5f, 15f)
            horizontalLineToRelative(-4f)
            close()
            moveTo(11f, 11.25f)
            horizontalLineToRelative(2f)
            verticalLineTo(10.5f)
            horizontalLineTo(11f)
            verticalLineToRelative(0.75f)
            close()
            moveToRelative(0f, 2.25f)
            horizontalLineToRelative(2f)
            verticalLineTo(12.75f)
            horizontalLineTo(11f)
            verticalLineTo(13.5f)
            close()
            moveTo(3f, 15f)
            verticalLineTo(10f)
            quadTo(3f, 9.57f, 3.29f, 9.29f)
            reflectiveQuadTo(4f, 9f)
            horizontalLineTo(7f)
            quadTo(7.43f, 9f, 7.71f, 9.29f)
            reflectiveQuadTo(8f, 10f)
            verticalLineToRelative(5f)
            horizontalLineTo(6.5f)
            verticalLineTo(13.5f)
            horizontalLineToRelative(-2f)
            verticalLineTo(15f)
            horizontalLineTo(3f)
            close()
            moveTo(4.5f, 12f)
            horizontalLineToRelative(2f)
            verticalLineTo(10.5f)
            horizontalLineToRelative(-2f)
            verticalLineTo(12f)
            close()
          }
        }
        .build()
    return _Abc!!
  }

private var _Abc: ImageVector? = null
