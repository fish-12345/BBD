package io.github.romanvht.byedpi.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object ThemeColors {
    val lightColor = lightColorScheme(
        primary = PrimaryLight,
        onPrimary = OnPrimaryLight,
        primaryContainer = PrimaryContainerLight,
        onPrimaryContainer = OnPrimaryContainerLight,
        inversePrimary = InversePrimaryLight,
        secondary = SecondaryLight,
        onSecondary = OnSecondaryLight,
        secondaryContainer = SecondaryContainerLight,
        onSecondaryContainer = OnSecondaryContainerLight,
        tertiary = TertiaryLight,
        onTertiary = OnTertiaryLight,
        tertiaryContainer = TertiaryContainerLight,
        onTertiaryContainer = OnTertiaryContainerLight,
        background = BackgroundLight,
        onBackground = OnBackgroundLight,
        surface = SurfaceLight,
        onSurface = OnSurfaceLight,
        surfaceVariant = SurfaceVariantLight,
        onSurfaceVariant = OnSurfaceVariantLight,
        surfaceTint = PrimaryLight,
        inverseSurface = InverseSurfaceLight,
        inverseOnSurface = InverseOnSurfaceLight,
        error = ErrorLight,
        onError = OnErrorLight,
        errorContainer = ErrorContainerLight,
        onErrorContainer = OnErrorContainerLight,
        outline = OutlineLight,
        outlineVariant = OutlineVariantLight,
        scrim = ScrimLight,
        surfaceBright = SurfaceBrightLight,
        surfaceContainer = SurfaceContainerLight,
        surfaceContainerHigh = SurfaceContainerHighLight,
        surfaceContainerHighest = SurfaceContainerHighestLight,
        surfaceContainerLow = SurfaceContainerLowLight,
        surfaceContainerLowest = SurfaceContainerLowestLight,
        surfaceDim = SurfaceDimLight,
    )

    val darkColor = darkColorScheme(
        primary = PrimaryDark,
        onPrimary = OnPrimaryDark,
        primaryContainer = PrimaryContainerDark,
        onPrimaryContainer = OnPrimaryContainerDark,
        inversePrimary = InversePrimaryDark,
        secondary = SecondaryDark,
        onSecondary = OnSecondaryDark,
        secondaryContainer = SecondaryContainerDark,
        onSecondaryContainer = OnSecondaryContainerDark,
        tertiary = TertiaryDark,
        onTertiary = OnTertiaryDark,
        tertiaryContainer = TertiaryContainerDark,
        onTertiaryContainer = OnTertiaryContainerDark,
        background = BackgroundDark,
        onBackground = OnBackgroundDark,
        surface = SurfaceDark,
        onSurface = OnSurfaceDark,
        surfaceVariant = SurfaceVariantDark,
        onSurfaceVariant = OnSurfaceVariantDark,
        surfaceTint = PrimaryDark,
        inverseSurface = InverseSurfaceDark,
        inverseOnSurface = InverseOnSurfaceDark,
        error = ErrorDark,
        onError = OnErrorDark,
        errorContainer = ErrorContainerDark,
        onErrorContainer = OnErrorContainerDark,
        outline = OutlineDark,
        outlineVariant = OutlineVariantDark,
        scrim = ScrimDark,
        surfaceBright = SurfaceBrightDark,
        surfaceContainer = SurfaceContainerDark,
        surfaceContainerHigh = SurfaceContainerHighDark,
        surfaceContainerHighest = SurfaceContainerHighestDark,
        surfaceContainerLow = SurfaceContainerLowDark,
        surfaceContainerLowest = SurfaceContainerLowestDark,
        surfaceDim = SurfaceDimDark,
    )
}

val A1_100 = Color(0xFFBBF294)
val A1_200 = Color(0xFFA0D57B)
val A1_400 = Color(0xFF6C9E4B)
val A1_700 = Color(0xFF245103)
val A1_800 = Color(0xFF163800)
val A1_900 = Color(0xFF0A2100)

val A2_100 = Color(0xFFD9E7CA)
val A2_200 = Color(0xFFBDCBAF)
val A2_500 = Color(0xFF6E7B63)
val A2_700 = Color(0xFF3E4A35)
val A2_800 = Color(0xFF283420)
val A2_900 = Color(0xFF141E0C)

val A3_100 = Color(0xFFBBECEB)
val A3_200 = Color(0xFFA0CFCE)
val A3_500 = Color(0xFF517F7E)
val A3_700 = Color(0xFF1E4E4E)
val A3_800 = Color(0xFF003737)
val A3_900 = Color(0xFF002020)

val N1_10 = Color(0xFFFCFCFC)
val N1_50 = Color(0xFFF1F1F1)
val N1_100 = Color(0xFFE2E2E2)
val N1_200 = Color(0xFFC6C6C6)
val N1_400 = Color(0xFF919191)
val N1_500 = Color(0xFF777777)
val N1_700 = Color(0xFF333333)
val N1_800 = Color(0xFF1F1F1F)
val N1_900 = Color(0xFF121212)

val PrimaryLight = A1_400           // 6C9E4B
val OnPrimaryLight = Color.White
val PrimaryContainerLight = A1_100  // BBF294
val OnPrimaryContainerLight = A1_900 // 0A2100

val PrimaryDark = A1_200            // A0D57B
val OnPrimaryDark = A1_800          // 163800
val PrimaryContainerDark = A1_700   // 245103
val OnPrimaryContainerDark = A1_100 // BBF294

val SecondaryLight = A2_500         // 6E7B63
val OnSecondaryLight = Color.White
val SecondaryContainerLight = A2_100 // D9E7CA
val OnSecondaryContainerLight = A2_900 // 141E0C

val SecondaryDark = A2_200          // BDCBAF
val OnSecondaryDark = A2_800        // 283420
val SecondaryContainerDark = A2_700 // 3E4A35
val OnSecondaryContainerDark = A2_100 // D9E7CA

val TertiaryLight = A3_500          // 517F7E
val OnTertiaryLight = Color.White
val TertiaryContainerLight = A3_100 // BBECEB
val OnTertiaryContainerLight = A3_900 // 002020

val TertiaryDark = A3_200           // A0CFCE
val OnTertiaryDark = A3_800         // 003737
val TertiaryContainerDark = A3_700  // 1E4E4E
val OnTertiaryContainerDark = A3_100 // BBECEB

val BackgroundLight = N1_10         // FCFCFC
val OnBackgroundLight = N1_900      // 1B1B1B
val BackgroundDark = N1_900         // 1B1B1B
val OnBackgroundDark = N1_100       // E2E2E2

val SurfaceLight = N1_10            // FCFCFC
val OnSurfaceLight = N1_900         // 1B1B1B
val SurfaceDark = N1_900            // 1B1B1B
val OnSurfaceDark = N1_100          // E2E2E2

val SurfaceVariantLight = N1_100    // E2E2E2
val OnSurfaceVariantLight = N1_700  // 474747
val SurfaceVariantDark = N1_700     // 474747
val OnSurfaceVariantDark = N1_200   // C6C6C6

val SurfaceBrightLight = N1_10      // FCFCFC
val SurfaceBrightDark = N1_800      // 303030

val SurfaceDimLight = N1_100        // E2E2E2
val SurfaceDimDark = N1_900         // 1B1B1B (Blackest)

val SurfaceContainerLowestLight = Color.White
val SurfaceContainerLowestDark = Color.Black

val SurfaceContainerLowLight = Color(0xFFF6F6F6) // Between N1_10 and N1_50
val SurfaceContainerLowDark = Color(0xFF191919)  // Between N1_900 and N1_800

val SurfaceContainerLight = N1_50     // F1F1F1 (Standard)
val SurfaceContainerDark = N1_800     // 303030

val SurfaceContainerHighLight = N1_100 // E2E2E2
val SurfaceContainerHighDark = N1_700  // 474747

val SurfaceContainerHighestLight = N1_50 // C6C6C6
val SurfaceContainerHighestDark = N1_700  // 5E5E5E

val InversePrimaryLight = PrimaryDark
val InversePrimaryDark = PrimaryLight
val InverseSurfaceLight = N1_800
val InverseSurfaceDark = N1_100
val InverseOnSurfaceLight = N1_50
val InverseOnSurfaceDark = N1_900

val ErrorLight = Color(0xFFBA1A1A)
val OnErrorLight = Color.White
val ErrorContainerLight = Color(0xFFFFDAD6)
val OnErrorContainerLight = Color(0xFF410002)

val ErrorDark = Color(0xFFFFB4AB)
val OnErrorDark = Color(0xFF690005)
val ErrorContainerDark = Color(0xFF93000A)
val OnErrorContainerDark = Color(0xFFFFDAD6)

// ===== Outline =====
val OutlineLight = N1_500 // 777777
val OutlineDark = N1_400  // 919191
val OutlineVariantLight = N1_200 // C6C6C6
val OutlineVariantDark = N1_700  // 474747

val ScrimLight = Color.Black.copy(alpha = 0.38f)
val ScrimDark = Color.Black.copy(alpha = 0.38f)