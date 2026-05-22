package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _appRegistration: ImageVector? = null

val IconsData.AppRegistration: ImageVector
    get() {
        if (_appRegistration != null) return _appRegistration!!
        _appRegistration = ImageVector.Builder(
            name = "AppRegistration",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(10.0f, 4.0f)
                horizontalLineTo(4.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(6.0f)
                verticalLineTo(4.0f)
                close()
                moveTo(8.0f, 8.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                close()
                moveTo(14.0f, 4.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(6.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(14.0f)
                close()
                moveTo(18.0f, 8.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(8.0f)
                close()
                moveTo(4.0f, 14.0f)
                verticalLineToRelative(6.0f)
                horizontalLineToRelative(6.0f)
                verticalLineToRelative(-6.0f)
                horizontalLineTo(4.0f)
                close()
                moveTo(8.0f, 18.0f)
                horizontalLineTo(6.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(2.0f)
                verticalLineTo(18.0f)
                close()
                moveTo(12.75f, 15.0f)
                lineToRelative(0.75f, 0.75f)
                lineTo(11.25f, 18.0f)
                horizontalLineTo(10.5f)
                verticalLineToRelative(-0.75f)
                lineTo(12.75f, 15.0f)
                close()
                moveTo(15.41f, 12.34f)
                lineToRelative(1.25f, 1.25f)
                lineToRelative(-4.16f, 4.16f)
                lineToRelative(-1.25f, -1.25f)
                lineTo(15.41f, 12.34f)
                close()
                moveTo(11.0f, 18.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(2.0f)
                lineToRelative(6.12f, -6.12f)
                lineToRelative(-2.0f, -2.0f)
                lineTo(11.0f, 18.0f)
                close()
                moveTo(20.71f, 11.29f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineToRelative(-0.59f, -0.59f)
                curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                lineToRelative(-0.59f, 0.59f)
                lineToRelative(2.0f, 2.0f)
                lineTo(20.71f, 11.29f)
                close()
            }
        }.build()
        return _appRegistration!!
    }
