package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Ico.DomainDisabled: ImageVector
  get() {
    if (_DomainDisabled != null) {
      return _DomainDisabled!!
    }
    _DomainDisabled =
      ImageVector.Builder(
          name = "DomainDisabled",
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
            moveTo(22f, 19.15f)
            lineToRelative(-2f, -2f)
            verticalLineTo(9f)
            horizontalLineTo(11.85f)
            lineTo(10f, 7.15f)
            verticalLineTo(5f)
            horizontalLineTo(7.85f)
            lineToRelative(-2f, -2f)
            horizontalLineTo(12f)
            verticalLineTo(7f)
            horizontalLineTo(22f)
            verticalLineTo(19.15f)
            close()
            moveTo(20.5f, 23.3f)
            lineTo(18.15f, 21f)
            horizontalLineTo(2f)
            verticalLineTo(4.8f)
            lineTo(0.7f, 3.5f)
            lineTo(2.1f, 2.1f)
            lineTo(21.9f, 21.9f)
            lineToRelative(-1.4f, 1.4f)
            close()
            moveTo(4f, 19f)
            horizontalLineTo(6f)
            verticalLineTo(17f)
            horizontalLineTo(4f)
            verticalLineToRelative(2f)
            close()
            moveTo(4f, 15f)
            horizontalLineTo(6f)
            verticalLineTo(13f)
            horizontalLineTo(4f)
            verticalLineToRelative(2f)
            close()
            moveTo(4f, 11f)
            horizontalLineTo(6f)
            verticalLineTo(9f)
            horizontalLineTo(4f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(4f, 8f)
            horizontalLineToRelative(2f)
            verticalLineTo(17f)
            horizontalLineTo(8f)
            verticalLineToRelative(2f)
            close()
            moveTo(8f, 15f)
            horizontalLineToRelative(2f)
            verticalLineTo(13f)
            horizontalLineTo(8f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(4f, 4f)
            horizontalLineToRelative(4.15f)
            lineToRelative(-2f, -2f)
            horizontalLineTo(12f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(6f, -6f)
            horizontalLineTo(16f)
            verticalLineTo(11f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(2f)
            close()
          }
        }
        .build()
    return _DomainDisabled!!
  }

private var _DomainDisabled: ImageVector? = null
