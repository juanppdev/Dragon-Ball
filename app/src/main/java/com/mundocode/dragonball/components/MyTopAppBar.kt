package com.mundocode.dragonball.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mundocode.dragonball.R




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavController, showBackButton: Boolean) {
    val customFont = FontFamily(
        Font(resId = R.font.saiyan_sans)
    )

    TopAppBar(
        title = {
            Row {
                LetterColors.lettersWithColors.forEach { (letter, color) ->
                    ColoredText(
                        letter = letter,
                        color = color,
                        fontFamily = customFont
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = Color.White,
            containerColor = Color(0xFF228B22)
        ),
        navigationIcon = if (showBackButton) {
            { NavigationIcon(navController) }
        } else {
            {}
        }
    )
}


@Composable
fun NavigationIcon(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

@Composable
fun ColoredText(letter: String, color: Color, fontFamily: FontFamily) {
    Text(
        text = letter,
        style = androidx.compose.ui.text.TextStyle(
            color = color,
            fontFamily = fontFamily,
            fontSize = 40.sp
        )
    )
}

object LetterColors {
    val lettersWithColors = listOf(
        "D" to Color(0xFFFFFF00),
        "r" to Color(0xFFFFFF00),
        "a" to Color(0xFFFFFF00),
        "g" to Color(0xFFFFFF00),
        "o" to Color(0xFFFF9800),
        "n" to Color(0xFFFFFF00),
        "B" to Color(0xFFFF0000),
        "a" to Color(0xFFFF0000),
        "l" to Color(0xFFFF0000),
        "l" to Color(0xFFFF0000)
    )
}