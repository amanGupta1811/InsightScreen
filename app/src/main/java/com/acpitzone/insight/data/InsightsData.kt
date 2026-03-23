package com.acpitzone.insight.data

import androidx.compose.ui.graphics.Color

data class CycleBar(
    val month: String,
    val totalDays: Int,
    val ovulationDay: Int,
    val menstruationStart: Float
)

data class WeightPoint(
    val month: String,
    val weight: Float
)


object InsightsData {

    val cycleBars = listOf(
        CycleBar("Jan", 28, ovulationDay = 14, menstruationStart = 0.75f),
        CycleBar("Feb", 30, ovulationDay = 15, menstruationStart = 0.72f),
        CycleBar("Mar", 28, ovulationDay = 14, menstruationStart = 0.75f),
        CycleBar("Apr", 32, ovulationDay = 16, menstruationStart = 0.69f),
        CycleBar("May", 28, ovulationDay = 14, menstruationStart = 0.75f),
        CycleBar("Jun", 28, ovulationDay = 14, menstruationStart = 0.75f),
        CycleBar("Jul", 29, ovulationDay = 14, menstruationStart = 0.74f),
        CycleBar("Aug", 31, ovulationDay = 15, menstruationStart = 0.71f),
        CycleBar("Sep", 28, ovulationDay = 14, menstruationStart = 0.75f),
        CycleBar("Oct", 30, ovulationDay = 15, menstruationStart = 0.73f),
        CycleBar("Nov", 27, ovulationDay = 13, menstruationStart = 0.76f),
        CycleBar("Dec", 28, ovulationDay = 14, menstruationStart = 0.75f),
        )

    val weightMonthly = listOf(
        WeightPoint("Jan", 32f),
        WeightPoint("Feb", 45f),
        WeightPoint("Mar", 40f),
        WeightPoint("Apr", 75f),
        WeightPoint("May", 55f),
    )

    val weightWeekly = listOf(
        WeightPoint("W1", 38f),
        WeightPoint("W2", 42f),
        WeightPoint("W3", 50f),
        WeightPoint("W4", 46f),
        WeightPoint("W5", 55f),
    )
}
