package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _domainDisabled: ImageVector? = null

val IconsData.DomainDisabled: ImageVector
    get() {
        if (_domainDisabled != null) return _domainDisabled!!
        _domainDisabled = ImageVector.Builder(
            name = "DomainDisabled",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(1.41f, 1.14f)
            lineTo(0.0f, 2.55f)
            lineToRelative(2.0f, 2.0f)
            verticalLineTo(21.0f)
            horizontalLineToRelative(16.45f)
            lineToRelative(3.0f, 3.0f)
            lineToRelative(1.41f, -1.41f)
            lineTo(1.41f, 1.14f)
            close()
            moveTo(10.0f, 19.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(2.0f)
            close()
            moveTo(10.0f, 15.0f)
            horizontalLineTo(4.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineTo(6.0f)
            verticalLineToRelative(2.0f)
            close()
            moveTo(4.0f, 11.0f)
            verticalLineTo(9.0f)
            horizontalLineToRelative(0.45f)
            lineToRelative(2.0f, 2.0f)
            horizontalLineTo(4.0f)
            close()
            moveTo(12.0f, 19.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(2.45f)
            lineToRelative(2.0f, 2.0f)
            horizontalLineTo(12.0f)
            close()
            moveTo(12.0f, 7.0f)
            verticalLineTo(5.18f)
            lineTo(13.82f, 7.0f)
            horizontalLineTo(12.0f)
            close()
            moveTo(20.0f, 7.0f)
            horizontalLineToRelative(-4.0f)
            verticalLineTo(5.0f)
            horizontalLineToRelative(4.0f)
            verticalLineTo(7.0f)
            close()
            moveTo(20.0f, 11.0f)
            horizontalLineToRelative(-4.0f)
            verticalLineTo(9.0f)
            horizontalLineToRelative(4.0f)
            verticalLineTo(11.0f)
            close()
            moveTo(20.0f, 15.0f)
            horizontalLineToRelative(-4.0f)
            verticalLineToRelative(-1.18f)
            lineToRelative(2.0f, 2.0f)
            verticalLineTo(15.0f)
            close()
            moveTo(22.0f, 14.72f)
            lineTo(20.0f, 12.72f)
            verticalLineTo(5.0f)
            horizontalLineTo(10.0f)
            verticalLineToRelative(1.18f)
            lineToRelative(-2.0f, -2.0f)
            verticalLineTo(3.0f)
            horizontalLineToRelative(14.0f)
            verticalLineTo(14.72f)
            close()
        }.build()
        return _domainDisabled!!
    }
