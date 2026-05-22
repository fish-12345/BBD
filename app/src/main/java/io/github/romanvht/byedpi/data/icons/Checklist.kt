package io.github.romanvht.byedpi.data.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _checklist: ImageVector? = null

val IconsData.Checklist: ImageVector
    get() {
        if (_checklist != null) return _checklist!!
        _checklist = ImageVector.Builder(
            name = "Checklist",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(8f, 7f)
                verticalLineTo(5f)
                horizontalLineTo(22f)
                verticalLineTo(7f)
                close()
                moveTo(8f, 13f)
                verticalLineTo(11f)
                horizontalLineTo(22f)
                verticalLineTo(13f)
                close()
                moveTo(8f, 19f)
                verticalLineTo(17f)
                horizontalLineTo(22f)
                verticalLineTo(19f)
                close()
                
                moveTo(3.55f, 7.55f)
                lineTo(2.125f, 6.125f)
                lineTo(2.825f, 5.425f)
                lineTo(3.55f, 6.15f)
                lineTo(5.175f, 4.525f)
                lineTo(5.875f, 5.225f)
                close()
                
                moveTo(3.55f, 13.55f)
                lineTo(2.125f, 12.125f)
                lineTo(2.825f, 11.425f)
                lineTo(3.55f, 12.15f)
                lineTo(5.175f, 10.525f)
                lineTo(5.875f, 11.225f)
                close()
                
                moveTo(3.55f, 19.55f)
                lineTo(2.125f, 18.125f)
                lineTo(2.825f, 17.425f)
                lineTo(3.55f, 18.15f)
                lineTo(5.175f, 16.525f)
                lineTo(5.875f, 17.225f)
                close()
            }
        }.build()
        return _checklist!!
    }
