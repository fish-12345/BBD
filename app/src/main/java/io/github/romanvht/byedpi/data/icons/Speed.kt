package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _speed: ImageVector? = null

val IconsData.Speed: ImageVector
    get() {
        if (_speed != null) return _speed!!
        _speed = ImageVector.Builder(
            name = "Speed",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).path(
            fill = SolidColor(Color(0xFF000000)),
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 18.0f)
            quadToRelative(1.15f, 0.0f, 1.95f, -0.8f)
            reflectiveQuadToRelative(0.8f, -1.95f)
            quadToRelative(0.0f, -1.15f, -0.8f, -1.95f)
            reflectiveQuadToRelative(-1.95f, -0.8f)
            quadToRelative(-1.15f, 0.0f, -1.95f, 0.8f)
            reflectiveQuadToRelative(-0.8f, 1.95f)
            quadToRelative(0.0f, 1.15f, 0.8f, 1.95f)
            reflectiveQuadToRelative(1.95f, 0.8f)
            close()
            moveTo(12.0f, 13.0f)
            lineTo(12.0f, 13.0f)
            close()
            moveTo(7.1f, 10.3f)
            lineTo(5.65f, 8.9f)
            quadToRelative(-0.275f, -0.275f, -0.275f, -0.7f)
            reflectiveQuadToRelative(0.275f, -0.7f)
            quadToRelative(0.275f, -0.275f, 0.7f, -0.275f)
            reflectiveQuadToRelative(0.7f, 0.275f)
            lineToRelative(1.45f, 1.4f)
            quadToRelative(0.3f, 0.275f, 0.288f, 0.688f)
            reflectiveQuadToRelative(-0.313f, 0.712f)
            quadToRelative(-0.3f, 0.275f, -0.7f, 0.275f)
            reflectiveQuadToRelative(-0.7f, -0.3f)
            close()
            moveTo(16.9f, 10.3f)
            quadToRelative(-0.3f, -0.3f, -0.3f, -0.712f)
            reflectiveQuadToRelative(0.3f, -0.688f)
            lineToRelative(1.45f, -1.4f)
            quadToRelative(0.275f, -0.275f, 0.7f, -0.275f)
            reflectiveQuadToRelative(0.7f, 0.275f)
            quadToRelative(0.275f, 0.275f, 0.275f, 0.7f)
            reflectiveQuadToRelative(-0.275f, 0.7f)
            lineToRelative(-1.45f, 1.4f)
            quadToRelative(-0.3f, 0.3f, -0.712f, 0.313f)
            reflectiveQuadToRelative(-0.688f, -0.313f)
            close()
            moveTo(12.0f, 7.0f)
            quadToRelative(-0.425f, 0.0f, -0.712f, -0.288f)
            reflectiveQuadToRelative(-0.288f, -0.712f)
            verticalLineToRelative(-2.0f)
            quadToRelative(0.0f, -0.425f, 0.288f, -0.712f)
            reflectiveQuadTo(12.0f, 3.0f)
            quadToRelative(0.425f, 0.0f, 0.713f, 0.288f)
            reflectiveQuadTo(13.0f, 4.0f)
            verticalLineToRelative(2.0f)
            quadToRelative(0.0f, 0.425f, -0.287f, 0.713f)
            reflectiveQuadTo(12.0f, 7.0f)
            close()
            moveTo(12.0f, 21.0f)
            quadToRelative(-3.125f, 0.0f, -5.575f, -1.825f)
            reflectiveQuadTo(3.225f, 14.35f)
            quadToRelative(-0.1f, -0.425f, 0.138f, -0.788f)
            reflectiveQuadToRelative(0.662f, -0.462f)
            quadToRelative(0.375f, -0.1f, 0.725f, 0.113f)
            reflectiveQuadToRelative(0.475f, 0.537f)
            quadToRelative(0.625f, 2.075f, 2.375f, 3.325f)
            reflectiveQuadTo(12.0f, 18.4f)
            quadToRelative(1.65f, 0.0f, 2.988f, -0.787f)
            reflectiveQuadToRelative(2.212f, -2.163f)
            lineToRelative(-1.85f, -1.85f)
            quadToRelative(-0.275f, -0.275f, -0.275f, -0.7f)
            reflectiveQuadToRelative(0.275f, -0.7f)
            quadToRelative(0.3f, -0.3f, 0.713f, -0.3f)
            reflectiveQuadToRelative(0.687f, 0.3f)
            lineToRelative(2.2f, 2.2f)
            quadToRelative(0.275f, 0.275f, 0.313f, 0.65f)
            reflectiveQuadToRelative(-0.113f, 0.75f)
            quadToRelative(-0.725f, 2.225f, -2.7f, 3.563f)
            reflectiveQuadTo(12.0f, 21.0f)
            close()
        }.build()
        return _speed!!
    }
