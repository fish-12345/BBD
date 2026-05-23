package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.HistoryEdu: ImageVector
  get() {
    if (_HistoryEdu != null) {
      return _HistoryEdu!!
    }
    _HistoryEdu =
      ImageVector.Builder(
          name = "HistoryEdu",
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
            moveTo(8f, 20f)
            quadTo(7.18f, 20f, 6.59f, 19.41f)
            reflectiveQuadTo(6f, 18f)
            verticalLineTo(15f)
            horizontalLineTo(9f)
            verticalLineTo(12.75f)
            quadTo(8.13f, 12.7f, 7.34f, 12.36f)
            reflectiveQuadTo(5.9f, 11.35f)
            verticalLineToRelative(-1.1f)
            horizontalLineTo(4.75f)
            lineTo(1.5f, 7f)
            quadTo(2.4f, 5.85f, 3.73f, 5.38f)
            reflectiveQuadTo(6.4f, 4.9f)
            quadTo(7.08f, 4.9f, 7.71f, 5f)
            reflectiveQuadTo(9f, 5.38f)
            verticalLineTo(4f)
            horizontalLineTo(21f)
            verticalLineTo(17f)
            quadToRelative(0f, 1.25f, -0.88f, 2.13f)
            reflectiveQuadTo(18f, 20f)
            horizontalLineTo(8f)
            close()
            moveToRelative(3f, -5f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(2f)
            quadToRelative(0f, 0.43f, 0.29f, 0.71f)
            reflectiveQuadTo(18f, 18f)
            reflectiveQuadToRelative(0.71f, -0.29f)
            quadTo(19f, 17.43f, 19f, 17f)
            verticalLineTo(6f)
            horizontalLineTo(11f)
            verticalLineTo(6.6f)
            lineToRelative(6f, 6f)
            verticalLineTo(14f)
            horizontalLineTo(15.6f)
            lineTo(12.75f, 11.15f)
            lineToRelative(-0.2f, 0.2f)
            quadTo(12.2f, 11.7f, 11.81f, 11.98f)
            reflectiveQuadTo(11f, 12.4f)
            verticalLineTo(15f)
            close()
            moveTo(5.6f, 8.25f)
            horizontalLineTo(7.9f)
            verticalLineTo(10.4f)
            quadToRelative(0.3f, 0.2f, 0.63f, 0.28f)
            reflectiveQuadTo(9.2f, 10.75f)
            quadToRelative(0.57f, 0f, 1.04f, -0.18f)
            reflectiveQuadTo(11.15f, 9.95f)
            lineToRelative(0.2f, -0.2f)
            lineTo(9.95f, 8.35f)
            quadTo(9.23f, 7.63f, 8.33f, 7.26f)
            reflectiveQuadTo(6.4f, 6.9f)
            quadTo(5.9f, 6.9f, 5.45f, 6.97f)
            reflectiveQuadTo(4.55f, 7.2f)
            lineTo(5.6f, 8.25f)
            close()
          }
        }
        .build()
    return _HistoryEdu!!
  }

private var _HistoryEdu: ImageVector? = null
