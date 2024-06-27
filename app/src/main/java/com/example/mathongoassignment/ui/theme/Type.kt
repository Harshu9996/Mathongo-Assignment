package com.example.mathongoassignment.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.mathongoassignment.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val inter = GoogleFont("inter")

val interFamily = FontFamily(
    Font(googleFont = inter, fontProvider = provider)
)

//val pixelBaseBold = GoogleFont("16px - Base/Bold")
//val pixelBaseBoldFamily = FontFamily(
//    Font(googleFont = pixelBaseBold, fontProvider = provider)
//)


// Set of Material typography styles to start with
val Typography = Typography(

//    Other default text styles to override
    displayLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Color(0xFFE54900)
    ),
    displayMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        //letter -2.5% is not available
//        letterSpacing = (-2.5).sp
    ),
    displaySmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 14.sp,
        textAlign = TextAlign.Center
    ),
    headlineSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(700),
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        //Paragraph 4 not available
//        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        //vacant
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        //letter -2.5% does not exists
        letterSpacing = (-2.5).sp
    ),
    titleSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center
    ),
    bodyLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        //Paragraph 16px not available
    ),
    bodyMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 14.sp,
    ),
    //Use body small for instructions and so body text
    bodySmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 14.4.sp,
//        letterSpacing = (-2.5).sp
    ),
    labelLarge = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    labelMedium = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
        lineHeight = 20.sp
        //Paragraph not available
    ),
    labelSmall = TextStyle(
        fontFamily = interFamily,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 20.sp,
//        letterSpacing = 4.sp
        //Paragraph not available
    ),


)