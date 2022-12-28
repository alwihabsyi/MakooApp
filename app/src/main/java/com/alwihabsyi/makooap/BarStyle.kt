package com.alwihabsyi.makooap

import android.content.Context
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import javax.inject.Inject

class BarStyle @Inject constructor(private val context: Context) {

    fun styleChart(barChart: BarChart) = barChart.apply {
        axisRight.isEnabled = false
        axisLeft.apply {
            isEnabled = true
            axisMinimum = 0f
        }

        xAxis.apply {
            axisMinimum = 0f
            isGranularityEnabled = true
            granularity = 4f
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
        }


        setTouchEnabled(true)
        isDragEnabled = true

        description = null
    }
}