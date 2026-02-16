package com.zerodevbuilds.eated.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zerodevbuilds.eated.ui.theme.ratingBgColor
import com.zerodevbuilds.eated.ui.theme.ratingColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingSelector(
    selectedRating: Int?,
    onRatingSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // "None" option
        val noneSelected = selectedRating == null
        val noneShape = RoundedCornerShape(12.dp)
        if (noneSelected) {
            Button(
                onClick = { onRatingSelected(null) },
                shape = noneShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 44.dp),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text(text = "—", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            OutlinedButton(
                onClick = { onRatingSelected(null) },
                shape = noneShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Gray.copy(alpha = 0.1f),
                    contentColor = Color.Gray
                ),
                border = BorderStroke(1.5.dp, Color.Gray.copy(alpha = 0.4f)),
                modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 44.dp),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Text(text = "—", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        for (i in 1..10) {
            val isSelected = i == selectedRating
            val color = ratingColor(i)
            val bgColor = ratingBgColor(i)
            val shape = RoundedCornerShape(12.dp)

            if (isSelected) {
                Button(
                    onClick = { onRatingSelected(i) },
                    shape = shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 44.dp),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(
                        text = "$i",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                OutlinedButton(
                    onClick = { onRatingSelected(i) },
                    shape = shape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = bgColor,
                        contentColor = color
                    ),
                    border = BorderStroke(1.5.dp, color.copy(alpha = 0.4f)),
                    modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 44.dp),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(
                        text = "$i",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
