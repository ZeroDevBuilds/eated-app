package com.zerodevbuilds.eated.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zerodevbuilds.eated.ui.theme.ratingBgColor
import com.zerodevbuilds.eated.ui.theme.ratingColor

@Composable
fun RatingBadge(
    rating: Int,
    modifier: Modifier = Modifier,
    label: String = "Overall: $rating/10",
    fontSize: TextUnit = 13.sp
) {
    val color = ratingColor(rating)
    val bg = ratingBgColor(rating)
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(bg)
            .border(1.dp, color, shape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RatingBadgeAvg(
    avg: Double,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp
) {
    val color = ratingColor(avg)
    val bg = ratingBgColor(avg)
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(bg)
            .border(1.dp, color.copy(alpha = 0.5f), shape)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = "Dish avg: %.1f".format(avg),
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold
        )
    }
}
