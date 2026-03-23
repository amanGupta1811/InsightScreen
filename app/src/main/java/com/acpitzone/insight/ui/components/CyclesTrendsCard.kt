package com.acpitzone.insight.ui.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
//import androidx.compose.material3.ripple
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.data.CycleBar
import com.acpitzone.insight.data.InsightsData
import com.acpitzone.insight.ui.theme.*
//import jdk.internal.javac.PreviewFeature


@Composable
fun CycleTrendsCard() {
    val allBars  = InsightsData.cycleBars
    val pageSize = 6
    var pageOffset by remember { mutableStateOf(maxOf(0, allBars.size - pageSize)) }

    val canGoBack    = pageOffset > 0
    val canGoForward = pageOffset + pageSize < allBars.size
    val visibleBars  = allBars.drop(pageOffset).take(pageSize)

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallArrowButton(
            icon    = Icons.Default.ChevronLeft,
            enabled = canGoBack,
            onClick = { pageOffset = maxOf(0, pageOffset - pageSize) }
        )
        Spacer(modifier = Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceWhite)
                .padding(vertical = 12.dp, horizontal = 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                visibleBars.forEach { bar ->
                    CycleBarItem(bar = bar)
                }
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        SmallArrowButton(
            icon    = Icons.Default.ChevronRight,
            enabled = canGoForward,
            onClick = { pageOffset = minOf(allBars.size - pageSize, pageOffset + pageSize) }
        )
    }
}

@Composable
fun SmallArrowButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .border(1.dp, if (enabled) Color(0xFFCCCCCC) else Color(0xFFEEEEEE), CircleShape)
            .background(Color.White)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
//                indication = ripple(bounded = true, radius = 14.dp),
                indication = rememberRipple(bounded = true, radius = 16.dp),

                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) TextPrimary else Color(0xFFCCCCCC),
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun NavArrowButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean,
    onClick: () -> Unit
) = SmallArrowButton(icon, enabled, onClick)

@Composable
fun CycleBarItem(bar: CycleBar) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(bar.month) {
        animProgress.snapTo(0f)
        animProgress.animateTo(1f, animationSpec = tween(600, easing = EaseOutCubic))
    }


    val minDays   = 26f
    val maxDays   = 34f
    val minHeight = 120.dp
    val maxHeight = 185.dp
    val barHeight: Dp = minHeight + (maxHeight - minHeight) *
            ((bar.totalDays - minDays) / (maxDays - minDays))
    val barWidth = 16.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text  = bar.totalDays.toString(),
            fontSize = 10.sp,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(3.dp))

        Box(
            modifier = Modifier
                .width(barWidth)
                .height(barHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animProgress.value)
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PurpleLighter)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.14f * animProgress.value)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(RedMenstruation.copy(alpha = 0.55f))
            )

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (-3).dp)
                    .clip(CircleShape)
                    .background(RedMenstruation.copy(alpha = 0.75f)),
                contentAlignment = Alignment.Center
            ) {
                Text("◷", fontSize = 6.sp, color = Color.White)
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = barHeight * 0.25f)
                    .clip(CircleShape)
                    .background(GreenCycle),
                contentAlignment = Alignment.Center
            ) {
                Text("◷", fontSize = 7.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text  = bar.month,
            fontSize = 10.sp,
            color = TextSecondary
        )
    }
}