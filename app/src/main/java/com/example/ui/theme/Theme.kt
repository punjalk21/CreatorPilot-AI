package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = ElectricViolet,
  onPrimary = TextWhite,
  primaryContainer = ObsidianMuted,
  onPrimaryContainer = NeonPurple,
  secondary = VioletGlow,
  onSecondary = TextWhite,
  secondaryContainer = ObsidianElevated,
  onSecondaryContainer = VioletLight,
  tertiary = IndigoAccent,
  onTertiary = TextWhite,
  background = ObsidianDark,
  onBackground = TextWhite,
  surface = ObsidianCard,
  onSurface = TextWhite,
  surfaceVariant = ObsidianElevated,
  onSurfaceVariant = TextGrayLight,
  outline = ObsidianCardBorder,
  outlineVariant = ObsidianMuted,
  error = ErrorRed,
  onError = TextWhite
)

private val LightColorScheme = lightColorScheme(
  primary = ElectricViolet,
  onPrimary = TextWhite,
  primaryContainer = LightCardElevated,
  onPrimaryContainer = ElectricViolet,
  secondary = IndigoAccent,
  onSecondary = TextWhite,
  secondaryContainer = LightBorder,
  onSecondaryContainer = LightTextPrimary,
  tertiary = VioletGlow,
  onTertiary = TextWhite,
  background = LightBg,
  onBackground = LightTextPrimary,
  surface = LightCard,
  onSurface = LightTextPrimary,
  surfaceVariant = LightCardElevated,
  onSurfaceVariant = LightTextSecondary,
  outline = LightBorder,
  outlineVariant = LightBorder,
  error = ErrorRed,
  onError = TextWhite
)

@Composable
fun CreatorPilotTheme(
  darkTheme: Boolean = true, // Dark-first aesthetic per product requirements
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

// Keep legacy alias for existing test compatibility
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  CreatorPilotTheme(darkTheme = darkTheme, content = content)
}
