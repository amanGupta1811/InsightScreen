package com.acpitzone.insight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.ui.theme.*

@Composable
fun SymptomTrendsCard() {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animProgress.animateTo(1f, animationSpec = tween(1000, easing = EaseOutCubic))
    }

    val gapDeg    = 5f
    val available = 360f - 4 * gapDeg

    data class Seg(val label: String, val pct: Int, val color: Color, val sweep: Float)
    val segments = listOf(
        Seg("Mood",     30, DonutMood,     30f / 99f * available),
        Seg("Bloating", 31, DonutBloating, 31f / 99f * available),
        Seg("Fatigue",  21, DonutFatigue,  21f / 99f * available),
        Seg("Acne",     17, DonutAcne,     17f / 99f * available),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp)
    ) {
        Text("Symptom Trends",         fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
        Text("Compared to last cycle", fontSize = 12.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(210.dp)
                    .align(Alignment.Center)
            ) {
                val strokeWidth = 42.dp.toPx()
                val radius   = (size.minDimension - strokeWidth) / 2f
                val topLeft  = Offset((size.width - radius * 2) / 2f, (size.height - radius * 2) / 2f)
                val arcSize  = Size(radius * 2, radius * 2)

                var startAngle = -150f
                segments.forEach { seg ->
                    drawArc(
                        color       = seg.color,
                        startAngle  = startAngle,
                        sweepAngle  = seg.sweep * animProgress.value,
                        useCenter   = false,
                        topLeft     = topLeft,
                        size        = arcSize,
                        style       = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    startAngle += seg.sweep + gapDeg
                }
            }


            OverlapLabel(
                percent  = 30,
                label    = "Mood",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-88).dp, y = (-78).dp)
            )
            OverlapLabel(
                percent  = 31,
                label    = "Bloating",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 72.dp, y = (-88).dp)
            )
            OverlapLabel(
                percent  = 17,
                label    = "Acne",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-92).dp, y = 70.dp)
            )
            OverlapLabel(
                percent  = 21,
                label    = "Fatigue",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 62.dp, y = 88.dp)
            )
        }
    }
}

@Composable
fun OverlapLabel(percent: Int, label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .background(Color.White)
            .size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = "$percent%",
                fontSize   = 13.sp,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
                textAlign  = TextAlign.Center
            )
            Text(
                text      = label,
                fontSize  = 11.sp,
                color     = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 13.sp
            )
        }
    }
}

@Composable
fun FloatingLabel(percent: Int, label: String, modifier: Modifier = Modifier) =
    OverlapLabel(percent, label, modifier)

@Composable
fun DonutLabel(percent: Int, label: String, modifier: Modifier = Modifier) =
    OverlapLabel(percent, label, modifier)