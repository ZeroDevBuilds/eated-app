package com.zerodevbuilds.eated.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zerodevbuilds.eated.ui.theme.ratingBgColor
import com.zerodevbuilds.eated.ui.theme.ratingColor

@Composable
fun RatingBadge(
    rating: Int?,
    modifier: Modifier = Modifier,
    label: String = if (rating != null) "Overall: $rating/10" else "Not rated",
    fontSize: TextUnit = 11.sp
) {
    val color = ratingBgColor(rating)
    val bg = ratingColor(rating)
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(bg)
            .border(1.dp, color, shape)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun RatingBadgeAvg(
    avg: Double,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 11.sp
) {
    val color = ratingBgColor(avg)
    val bg = ratingColor(avg)
    val shape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(bg)
            .border(1.dp, color.copy(alpha = 0.5f), shape)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "Dish avg: %.1f".format(avg),
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
