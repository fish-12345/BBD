package io.github.romanvht.byedpi.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.github.romanvht.byedpi.data.ThemeManager

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrackerTheme(
    themeManager: ThemeManager,
    content: @Composable () -> Unit
) {
    val managerDynamic by themeManager.isDynamicColor.collectAsState(initial = themeManager.getDynamicColor())
    val managerDark by themeManager.isDarkTheme.collectAsState(initial = themeManager.getDarkTheme())
    val selectedSchemeName by themeManager.selectedColorScheme.collectAsState(initial = themeManager.getSelectedColorScheme())

    val isDark = when (managerDark) {
        true -> true
        false -> false
        null -> isSystemInDarkTheme()
    }

    val colorScheme = getColorScheme(
        isDark = isDark,
        isDynamic = managerDynamic,
        schemeName = selectedSchemeName
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDark
                isAppearanceLightNavigationBars = !isDark
            }
        }
    }

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private object ThemePresets {
    val map = mapOf(
        "Default" to (DefaultLightColors to DefaultDarkColors),
        "Blue"    to (BlueLightColors to BlueDarkColors),
        "Red"     to (RedLightColors to RedDarkColors),
        "Yellow"  to (YellowLightColors to YellowDarkColors),
        "Orange"  to (OrangeLightColors to OrangeDarkColors),
        "Purple"  to (PurpleLightColors to PurpleDarkColors),
        "Pink"    to (PinkLightColors to PinkDarkColors),
        "Coffee"  to (CoffeeLightColors to CoffeeDarkColors)
    )

    val fallback = DefaultLightColors to DefaultDarkColors
}

@Composable
private fun getColorScheme(
    isDark: Boolean,
    isDynamic: Boolean,
    schemeName: String
): ColorScheme {
    val context = LocalContext.current

    return when {
        isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            val pair = ThemePresets.map[schemeName] ?: ThemePresets.fallback
            if (isDark) pair.second else pair.first
        }
    }
}
