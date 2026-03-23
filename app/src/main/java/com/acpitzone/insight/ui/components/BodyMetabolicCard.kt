package com.acpitzone.insight.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acpitzone.insight.data.InsightsData
import com.acpitzone.insight.data.WeightPoint
import com.acpitzone.insight.ui.theme.*

@Composable
fun BodyMetabolicCard() {
    var isMonthly by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Your weight", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(text = "in kg", fontSize = 12.sp, color = TextSecondary)
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFF0F0F5))
                    .padding(3.dp)
            ) {
                ToggleChip(label = "Monthly", selected = isMonthly, onClick = { isMonthly = true })
                ToggleChip(label = "Weekly", selected = !isMonthly, onClick = { isMonthly = false })
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        val data = if (isMonthly) InsightsData.weightMonthly else InsightsData.weightWeekly
        WeightLineChart(data = data)
    }
}

@Composable
fun ToggleChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) TextPrimary else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color.White else TextSecondary
        )
    }
}

@Composable
fun WeightLineChart(data: List<WeightPoint>) {
    val animProgress = remember(data) { Animatable(0f) }
    LaunchedEffect(data) {
        animProgress.snapTo(0f)
        animProgress.animateTo(1f, animationSpec = tween(1000, easing = EaseInOutCubic))
    }

    val maxW = (data.maxOf { it.weight } * 1.15f).coerceAtLeast(80f)
    val minW = 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val leftPad = 36f
            val bottomPad = 28f
            val topPad = 10f
            val chartW = w - leftPad - 8f
            val chartH = h - bottomPad - topPad

            fun xAt(i: Int): Float = leftPad + (i.toFloat() / (data.size - 1)) * chartW
            fun yAt(v: Float): Float = topPad + chartH * (1f - (v - minW) / (maxW - minW))

            listOf(25f, 50f, 75f).forEach { yVal ->
                val yPos = yAt(yVal)
                drawLine(
                    color = Color(0xFFEEEEEE),
                    start = Offset(leftPad, yPos),
                    end = Offset(w - 8f, yPos),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val animatedPts = mutableListOf<Offset>()
            val totalPts = data.size
            val floatIdx = animProgress.value * (totalPts - 1)
            for (i in 0 until totalPts) {
                if (i <= floatIdx) {
                    animatedPts.add(Offset(xAt(i), yAt(data[i].weight)))
                } else if (i > 0 && (i - 1) < floatIdx) {
                    // interpolate
                    val frac = floatIdx - (i - 1)
                    val prevY = yAt(data[i - 1].weight)
                    val nextY = yAt(data[i].weight)
                    val prevX = xAt(i - 1)
                    val nextX = xAt(i)
                    animatedPts.add(Offset(prevX + frac * (nextX - prevX), prevY + frac * (nextY - prevY)))
                    break
                }
            }

            if (animatedPts.size < 2) return@Canvas

            val linePath = Path()
            linePath.moveTo(animatedPts[0].x, animatedPts[0].y)
            for (i in 1 until animatedPts.size) {
                val prev = animatedPts[i - 1]
                val curr = animatedPts[i]
                val cpX = (prev.x + curr.x) / 2f
                linePath.cubicTo(cpX, prev.y, cpX, curr.y, curr.x, curr.y)
            }

            val fillPath = Path()
            fillPath.addPath(linePath)
            fillPath.lineTo(animatedPts.last().x, topPad + chartH)
            fillPath.lineTo(leftPad, topPad + chartH)
            fillPath.close()
            drawPath(
                fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(ChartPink.copy(alpha = 0.6f), ChartPinkArea.copy(alpha = 0.1f)),
                    startY = topPad,
                    endY = topPad + chartH
                )
            )

            drawPath(linePath, color = ChartPink, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))

            animatedPts.forEachIndexed { i, pt ->
                if (i == animatedPts.size - 1 && animProgress.value < 0.99f) return@forEachIndexed
                drawCircle(color = Color.White, radius = 5.dp.toPx(), center = pt)
                drawCircle(color = ChartPink, radius = 4.dp.toPx(), center = pt, style = Stroke(width = 1.5.dp.toPx()))
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxHeight()
                .padding(bottom = 28.dp, top = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("75", "50", "25").forEach { label ->
                Text(text = label, fontSize = 10.sp, color = TextSecondary)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = 36.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { pt ->
                Text(text = pt.month, fontSize = 10.sp, color = TextSecondary)
            }
        }
    }
}
