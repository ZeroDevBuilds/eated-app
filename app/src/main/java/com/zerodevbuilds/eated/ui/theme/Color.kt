package com.zerodevbuilds.eated.ui.theme

import androidx.compose.ui.graphics.Color

// Primary — Deep coral/red-orange
val Primary = Color(0xFFC75F71)
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFFFDAD5)
val OnPrimaryContainer = Color(0xFF410002)

// Secondary — Warm teal
val Secondary = Color(0xFF00796B)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFB2DFDB)
val OnSecondaryContainer = Color(0xFF002019)

// Tertiary — Amber gold
val Tertiary = Color(0xFFF9A825)
val OnTertiary = Color(0xFF3E2700)
val TertiaryContainer = Color(0xFFFFE082)
val OnTertiaryContainer = Color(0xFF2B1700)

// Error
val Error = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)
val ErrorContainer = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF410002)

// Light surfaces
val LightBackground = Color(0xFFFFFBFF)
val LightSurface = Color(0xFFFFFBFF)
val LightSurfaceVariant = Color(0xFFF5DDDA)
val LightSurfaceContainer = Color(0xFFFCEEEC)
val LightSurfaceContainerHigh = Color(0xFFF7E4E1)
val LightOnBackground = Color(0xFF201A19)
val LightOnSurface = Color(0xFF201A19)
val LightOnSurfaceVariant = Color(0xFF534341)
val LightOutline = Color(0xFF857371)

// Dark surfaces
val DarkBackground = Color(0xFF201A19)
val DarkSurface = Color(0xFF201A19)
val DarkSurfaceVariant = Color(0xFF534341)
val DarkSurfaceContainer = Color(0xFF2D2524)
val DarkSurfaceContainerHigh = Color(0xFF382F2E)
val DarkOnBackground = Color(0xFFEDE0DE)
val DarkOnSurface = Color(0xFFEDE0DE)
val DarkOnSurfaceVariant = Color(0xFFD8C2BF)
val DarkOutline = Color(0xFFA08C8A)

// Dark primary
val DarkPrimary = Color(0xFFC75F71)
val DarkOnPrimary = Color(0xFF690005)
val DarkPrimaryContainer = Color(0xFF93000A)
val DarkOnPrimaryContainer = Color(0xFFFFDAD5)

// Dark secondary
val DarkSecondary = Color(0xFF80CBC4)
val DarkOnSecondary = Color(0xFF00382F)
val DarkSecondaryContainer = Color(0xFF005046)
val DarkOnSecondaryContainer = Color(0xFFA7F0E6)

// Dark tertiary
val DarkTertiary = Color(0xFFFFCC80)
val DarkOnTertiary = Color(0xFF4A2800)
val DarkTertiaryContainer = Color(0xFF6B3B00)
val DarkOnTertiaryContainer = Color(0xFFFFDDB3)

// Rating tier colors
val RatingGood = Color(0xFF2E7D32)
val RatingOkay = Color(0xFFF28F3B)
val RatingBad = Color(0xFFC62828)

val RatingGoodBg = Color(0xFFE8F5E9)
val RatingOkayBg = Color(0xFFFFF3E0)
val RatingBadBg = Color(0xFFFFEBEE)

val RatingNone = Color(0xFF9E9E9E)
val RatingNoneBg = Color(0xFFF5F5F5)

fun ratingColor(value: Int?): Color = when {
    value == null -> RatingNone
    value >= 8 -> RatingGood
    value >= 5 -> RatingOkay
    else -> RatingBad
}

fun ratingColor(value: Double): Color = when {
    value >= 8.0 -> RatingGood
    value >= 5.0 -> RatingOkay
    else -> RatingBad
}

fun ratingBgColor(value: Int?): Color = when {
    value == null -> RatingNoneBg
    value >= 8 -> RatingGoodBg
    value >= 5 -> RatingOkayBg
    else -> RatingBadBg
}

fun ratingBgColor(value: Double): Color = when {
    value >= 8.0 -> RatingGoodBg
    value >= 5.0 -> RatingOkayBg
    else -> RatingBadBg
}
