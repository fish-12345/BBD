package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _history: ImageVector? = null

val IconsData.History: ImageVector
    get() {
        if (_history != null) return _history!!
        _history = ImageVector.Builder(
            name = "History",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.5f, 15.25f)
                lineTo(15.9f, 11.85f)
                lineTo(14.85f, 10.8f)
                lineTo(12.0f, 13.65f)
                verticalLineTo(8.0f)
                horizontalLineTo(10.5f)
                verticalLineTo(14.25f)
                lineTo(12.5f, 15.25f)
                close()
                moveTo(12.02f, 20.0f)
                curveToRelative(-1.88f, 0.0f, -3.54f, -0.55f, -4.98f, -1.65f)
                curveToRelative(-1.44f, -1.1f, -2.43f, -2.53f, -2.97f, -4.3f)
                horizontalLineTo(5.62f)
                curveToRelative(0.52f, 1.37f, 1.4f, 2.47f, 2.65f, 3.32f)
                curveToRelative(1.25f, 0.85f, 2.58f, 1.28f, 4.02f, 1.28f)
                curveToRelative(2.05f, 0.0f, 3.79f, -0.72f, 5.22f, -2.15f)
                curveToRelative(1.43f, -1.43f, 2.15f, -3.17f, 2.15f, -5.22f)
                curveToRelative(0.0f, -2.05f, -0.72f, -3.79f, -2.15f, -5.22f)
                curveToRelative(-1.43f, -1.43f, -3.17f, -2.15f, -5.22f, -2.15f)
                curveToRelative(-1.27f, 0.0f, -2.43f, 0.38f, -3.48f, 1.15f)
                curveToRelative(-1.05f, 0.77f, -1.87f, 1.77f, -2.45f, 3.0f)
                lineTo(9.5f, 8.05f)
                verticalLineTo(4.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(9.5f)
                horizontalLineTo(9.5f)
                lineTo(7.35f, 7.35f)
                curveToRelative(0.48f, -0.92f, 1.14f, -1.66f, 1.98f, -2.23f)
                curveToRelative(0.84f, -0.57f, 1.73f, -0.85f, 2.67f, -0.85f)
                curveToRelative(1.63f, 0.0f, 3.03f, 0.58f, 4.18f, 1.72f)
                curveToRelative(1.15f, 1.14f, 1.72f, 2.54f, 1.72f, 4.18f)
                curveToRelative(0.0f, 1.63f, -0.58f, 3.03f, -1.72f, 4.18f)
                curveToRelative(-1.14f, 1.15f, -2.54f, 1.72f, -4.18f, 1.72f)
                close()
            }
        }.build()
        return _history!!
    }
