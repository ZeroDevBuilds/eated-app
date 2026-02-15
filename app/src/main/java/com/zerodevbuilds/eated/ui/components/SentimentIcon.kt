package com.zerodevbuilds.eated.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun SentimentIcon(
    rating: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp
) {
    val emoji = when {
        rating >= 8 -> "\uD83D\uDE0A"
        rating >= 5 -> "\uD83D\uDE10"
        else -> "\uD83D\uDE1E"
    }
    Text(text = emoji, fontSize = fontSize, modifier = modifier)
}
