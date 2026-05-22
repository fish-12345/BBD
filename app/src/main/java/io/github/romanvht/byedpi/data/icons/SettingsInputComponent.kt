package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _settingsInputComponent: ImageVector? = null

val IconsData.SettingsInputComponent: ImageVector
    get() {
        if (_settingsInputComponent != null) return _settingsInputComponent!!
        _settingsInputComponent = ImageVector.Builder(
            name = "SettingsInputComponent",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2.0f, 21.0f)
                verticalLineToRelative(-12.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(4.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-10.0f)
                horizontalLineToRelative(-2.0f)
                close()

                moveTo(10.0f, 21.0f)
                verticalLineToRelative(-12.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(12.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-10.0f)
                horizontalLineToRelative(-2.0f)
                close()

                moveTo(18.0f, 21.0f)
                verticalLineToRelative(-12.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(20.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-10.0f)
                horizontalLineToRelative(-2.0f)
                close()

                moveTo(2.0f, 7.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(4.0f, 5.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                close()

                moveTo(10.0f, 7.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(12.0f, 5.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                close()

                moveTo(18.0f, 7.0f)
                verticalLineToRelative(-4.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(4.0f)
                close()
                moveTo(20.0f, 5.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }.build()
        return _settingsInputComponent!!
    }
