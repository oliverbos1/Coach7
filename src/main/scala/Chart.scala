package com.oliverbos1.coach7

import org.knowm.xchart.{SwingWrapper, XYChartBuilder}

import java.awt.Toolkit

object Chart {

  private val screenRes = Toolkit.getDefaultToolkit.getScreenSize
  private val width: Int = (0.3125 * screenRes.width).round.toInt
  private val height: Int = (0.5 * width).round.toInt

  def display(values: List[(Double, Double)], xTitle: String, yTitle: String): Unit = {
    val chart = new XYChartBuilder()
      .width(width)
      .height(height)
      .title("Result")
      .xAxisTitle(xTitle)
      .yAxisTitle(yTitle)
      .build()

    val (xValues, yValues) = values.unzip
    chart.addSeries("Bleah", xValues.toArray, yValues.toArray)

    new SwingWrapper(chart).displayChart()
  }

}
