package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _batteryAlert: ImageVector? = null

val IconsData.BatteryAlert: ImageVector
    get() {
        if (_batteryAlert != null) return _batteryAlert!!
        _batteryAlert = ImageVector.Builder(
            name = "BatteryAlert",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11.0f, 18.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(11.0f, 14.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(7.0f)
                close()
                moveTo(7.0f, 22.0f)
                quadToRelative(-0.825f, 0.0f, -1.412f, -0.587f)
                quadTo(5.0f, 20.825f, 5.0f, 20.0f)
                verticalLineTo(6.0f)
                quadToRelative(0.0f, -0.825f, 0.588f, -1.412f)
                quadTo(6.175f, 4.0f, 7.0f, 4.0f)
                horizontalLineToRelative(3.0f)
                verticalLineTo(2.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(3.0f)
                quadToRelative(0.825f, 0.0f, 1.413f, 0.588f)
                quadTo(19.0f, 5.175f, 19.0f, 6.0f)
                verticalLineToRelative(14.0f)
                quadToRelative(0.0f, 0.825f, -0.587f, 1.413f)
                quadTo(17.825f, 22.0f, 17.0f, 22.0f)
                horizontalLineTo(7.0f)
                close()
                moveTo(7.0f, 20.0f)
                horizontalLineToRelative(10.0f)
                verticalLineTo(6.0f)
                horizontalLineTo(7.0f)
                verticalLineToRelative(14.0f)
                close()
            }
        }.build()
        return _batteryAlert!!
    }
