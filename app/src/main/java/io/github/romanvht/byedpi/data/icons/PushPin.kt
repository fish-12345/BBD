package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _pushPin: ImageVector? = null
private var _pushPinOutlined: ImageVector? = null

val IconsData.PushPin: ImageVector
    get() {
        if (_pushPin != null) return _pushPin!!
        _pushPin = ImageVector.Builder(
            name = "PushPin",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(16f, 9f)
            verticalLineTo(4f)
            horizontalLineToRelative(1f)
            curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
            reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
            horizontalLineTo(7f)
            curveToRelative(-0.55f, 0f, -1f, 0.45f, -1f, 1f)
            reflectiveCurveToRelative(0.45f, 1f, 1f, 1f)
            horizontalLineToRelative(1f)
            verticalLineToRelative(5f)
            curveToRelative(0f, 1.66f, -1.34f, 3f, -3f, 3f)
            verticalLineToRelative(2f)
            horizontalLineTo(11f)
            verticalLineToRelative(7f)
            lineToRelative(1f, 1f)
            lineToRelative(1f, -1f)
            verticalLineToRelative(-7f)
            horizontalLineTo(19f)
            verticalLineToRelative(-2f)
            curveToRelative(-1.66f, 0f, -3f, -1.34f, -3f, -3f)
            close()
        }.build()
        return _pushPin!!
    }

val IconsData.PushPinOutlined: ImageVector
    get() {
        if (_pushPinOutlined != null) return _pushPinOutlined!!
        _pushPinOutlined = ImageVector.Builder(
            name = "PushPinOutlined",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 22f)
                lineToRelative(-1f, -1f)
                verticalLineToRelative(-7f)
                horizontalLineTo(5f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineTo(4f)
                horizontalLineTo(6f)
                verticalLineTo(2f)
                horizontalLineToRelative(12f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(8f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-6f)
                verticalLineToRelative(7f)
                close()
                moveTo(9f, 12f)
                horizontalLineToRelative(6f)
                verticalLineTo(4f)
                horizontalLineTo(9f)
                close()
            }
        }.build()
        return _pushPinOutlined!!
    }
