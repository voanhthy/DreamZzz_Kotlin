package de.syntax_institut.androidabschlussprojekt.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R

val font_manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal)
)

val font_andreas = FontFamily(
    Font(R.font.andreas, FontWeight.Normal)
)

val font_ibmplexmono = FontFamily(
    Font(R.font.ibmplexmono_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = font_manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    titleLarge = TextStyle(
        fontFamily = font_andreas,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.sp
    ),

    labelSmall = TextStyle(
        fontFamily = font_ibmplexmono,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)


