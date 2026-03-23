package com.acpitzone.insight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StabilitySummaryCard() {
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animProgress.animateTo(
            1f,
            animationSpec = tween(1400, easing = EaseInOutCubic)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(
            text = "Based on your recent logs and symptom\npatterns.",
            color = Color(0xFF6B6B8A),
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Stability Score", fontSize = 13.sp, color = Color(0xFF6B6B8A))

        Text(
            "78%",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E)
        )

        Spacer(modifier = Modifier.height(12.dp))

        StabilityChart(animProgress= animProgress.value)
    }
}



@Composable
fun StabilityChart(animProgress: Float) {

    val yLabelWidth = 36.dp
    val chartHeight = 120.dp
    val xLabelHeight = 24.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(chartHeight + xLabelHeight)
    ) {

        Column(
            modifier = Modifier
                .width(yLabelWidth)
                .height(chartHeight),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("32d", "28d", "24d").forEach {
                Text(it, fontSize = 10.sp, color = Color(0xFF6B6B8A))
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
                .padding(start = yLabelWidth)
        ) {

            val w = size.width
            val h = size.height

            fun x(i: Int) = i * w / 3f
            fun y(v: Float) = h * (1f - (v - 24f) / 8f)

            // 🔥 Figma EXACT linear data
            val outer = listOf(24f, 25f, 27f, 30f)
            val mid   = listOf(24f, 24.7f, 26f, 28f)
            val inner = listOf(24f, 24.3f, 25.2f, 26.5f)

            fun buildPath(values: List<Float>): Path {
                return Path().apply {
                    moveTo(0f, h)
                    lineTo(x(0), y(values[0]))

                    for (i in 1..3) {
                        lineTo(x(i), y(values[i]))
                    }

                    lineTo(x(3), h)
                    close()
                }
            }

            drawPath(
                buildPath(outer),
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFFB8AFDF).copy(alpha = 0.25f)
                    ),
                    startY = h * 0.4f,
                    endY = h
                )
            )

            drawPath(
                buildPath(mid),
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF9F8FEF).copy(alpha = 0.35f)
                    ),
                    startY = h * 0.45f,
                    endY = h
                )
            )

            drawPath(
                buildPath(inner),
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF7E6BDB).copy(alpha = 0.45f)
                    ),
                    startY = h * 0.5f,
                    endY = h
                )
            )

            val marX = x(2)
            val marY = y(inner[2])

            var cy = marY + 6f
            while (cy < h) {
                drawLine(
                    Color.Gray.copy(alpha = 0.4f),
                    Offset(marX, cy),
                    Offset(marX, cy + 6f),
                    strokeWidth = 1.dp.toPx()
                )
                cy += 10f
            }


            drawCircle(Color(0xFF5A9E8A).copy(alpha = 0.2f), 10.dp.toPx(), Offset(marX, marY))
            drawCircle(Color(0xFF5A9E8A), 5.dp.toPx(), Offset(marX, marY))
            drawCircle(Color.White, 2.dp.toPx(), Offset(marX, marY))
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = yLabelWidth)
                .offset(x = (2 * 80).dp, y = (-6).dp) // tweak if needed
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                "Stability\nImproving",
                color = Color.White,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = yLabelWidth),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Jan", "Feb", "Mar", "Apr").forEach {
                Text(
                    it,
                    fontSize = 11.sp,
                    color = if (it == "Mar") Color.Black else Color.Gray,
                    fontWeight = if (it == "Mar") FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}
