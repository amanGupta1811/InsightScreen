package com.acpitzone.insight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.ui.theme.*



data class CorrelationRow(
    val label: String,
    val baseColor: Color,
    val filledCount: Int,
    val total: Int = 10
)

@Composable
fun LifestyleImpactCard() {
    var selectedPeriod by remember { mutableStateOf("4 months") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val periodOptions = listOf("1 month", "2 months", "3 months", "4 months", "6 months")

    // Update filled counts based on selected period
    val periodMultiplier = when (selectedPeriod) {
        "1 month" -> 0.25f
        "2 months" -> 0.5f
        "3 months" -> 0.75f
        "4 months" -> 1.0f
        "6 months" -> 1.2f
        else -> 1.0f
    }

    val rows = listOf(
        CorrelationRow("Sleep",    SleepColor,    filledCount = (8 * periodMultiplier).toInt().coerceIn(1, 10)),
        CorrelationRow("Hydrate",  HydrateColor,  filledCount = (3 * periodMultiplier).toInt().coerceIn(1, 10)),
        CorrelationRow("Caffeine", CaffeineColor, filledCount = (5 * periodMultiplier).toInt().coerceIn(1, 10)),
        CorrelationRow("Exercise", ExerciseColor, filledCount = (4 * periodMultiplier).toInt().coerceIn(1, 10)),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Correlation Strength",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF2F2F5))
                        .clickable { dropdownExpanded = true }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = selectedPeriod,
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select period",
                        tint = TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    periodOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    fontSize = 13.sp,
                                    color = if (option == selectedPeriod) PurplePrimary else TextPrimary,
                                    fontWeight = if (option == selectedPeriod) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                selectedPeriod = option
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        rows.forEach { row ->
            CorrelationRowItem(row = row)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CorrelationRowItem(row: CorrelationRow) {
    val animProgress = remember(row.filledCount) { Animatable(0f) }
    LaunchedEffect(row.filledCount) {
        animProgress.snapTo(0f)
        animProgress.animateTo(1f, animationSpec = tween(700, easing = EaseOutCubic))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.label,
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.width(60.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            for (i in 0 until row.total) {
                val isFilled = i < row.filledCount
                val intensity = if (isFilled) {
                    1f - (i.toFloat() / row.filledCount) * 0.4f
                } else 0f

                val animatedAlpha = if (isFilled) {
                    val threshold = i.toFloat() / row.filledCount
                    ((animProgress.value - threshold * 0.5f) * 2f).coerceIn(0f, 1f)
                } else 1f

                val color = if (isFilled) {
                    lerp(row.baseColor, row.baseColor.copy(alpha = 0.3f), 1f - intensity)
                        .copy(alpha = animatedAlpha)
                } else {
                    Color(0xFFEEEEF2)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color)
                )
            }
        }
    }
}