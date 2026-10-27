package com.oliverbos1.coach7

import org.knowm.xchart.internal.series.Series
import org.knowm.xchart.style.markers.SeriesMarkers
import org.knowm.xchart.{SwingWrapper, XYChart, XYChartBuilder}

import java.awt.{Color, Toolkit}
import scala.jdk.CollectionConverters.*

object Chart {

  private val screenRes = Toolkit.getDefaultToolkit.getScreenSize
  private val width: Int = (0.45 * screenRes.width).round.toInt
  private val height: Int = (0.5 * width).round.toInt

  def display(values: List[(Double, Double)], xTitle: String, yTitle: String, color: Color): Unit = {
    val chart = new XYChartBuilder()
      .width(width)
      .height(height)
      .xAxisTitle(xTitle)
      .yAxisTitle(yTitle)
      .build()
    chart.getStyler.setLegendVisible(false)

    // Adding x- and y-axis by adding a series
    addAxises(values.map(_._1).min, values.map(_._1).max, values.map(_._2).min, values.map(_._2).max, chart)

    val (xValues, yValues) = values.unzip
    chart.addSeries("Bleah", xValues.toArray, yValues.toArray)
      .setMarker(SeriesMarkers.NONE).setLineColor(color).setShowInLegend(false)

    new SwingWrapper(chart).displayChart()
  }

  def display(values: List[(Double, Double, Double)], xTitle: String, y1Title: String, y2Title: String,
              color1: Color = Color.RED, color2: Color = Color.BLUE): Unit = {
    val xMin = values.map(_._1).min
    val xMax = values.map(_._1).max
    val y1Min = values.map(_._2).min
    val y1Max = values.map(_._2).max
    val y2Max = values.map(_._3).max
    val y2Min = values.map(_._3).min

    val chart = buildChart(xTitle, y1Title)
    chart.getStyler.setLegendVisible(false)
    chart.getStyler.setXAxisMin(0.0)

    val chart2 = buildChart(xTitle, y2Title)


    chart2.getStyler.setLegendVisible(false)
    chart2.getStyler.setXAxisMin(xMin)

    // Adding x- and y-axis by adding a series
    addAxises(xMin, xMax, y1Min, y1Max, chart)
    addAxises(xMin, xMax, y2Min, y2Max, chart2)

    val chartData1 = values.map { case (times, y1, _) =>
      (times, y1)
    }
    val chartData2 = values.map { case (times, _, y2) =>
      (times, y2)
    }

    val (x1, y1) = chartData1.unzip
    val (x2, y2) = chartData2.unzip

    chart.addSeries("1", x1.toArray, y1.toArray)
      .setMarker(SeriesMarkers.NONE).setLineColor(color1).setShowInLegend(false)
    chart2.addSeries("2", x2.toArray, y2.toArray)
      .setMarker(SeriesMarkers.NONE).setLineColor(color2).setShowInLegend(false)

    new SwingWrapper[XYChart](List(chart, chart2).asJava, 2, 1).setTitle("Results").displayChartMatrix()
  }

  def buildChart(xTitle: String, yTitle: String): XYChart = {
    new XYChartBuilder()
      .width(width)
      .height(height)
      .xAxisTitle(xTitle)
      .yAxisTitle(yTitle)
      .build()
  }

  def addAxises(xMin: Double, xMax: Double, yMin: Double, yMax: Double, chart: XYChart): Series = {
    chart.addSeries("x", Array(xMin, xMax), Array(0.0, 0.0))
      .setMarker(SeriesMarkers.NONE)
      .setLineColor(Color.GRAY)
      .setShowInLegend(false)
    chart.addSeries("y", Array(0.0, 0.0), Array(yMin, yMax))
      .setMarker(SeriesMarkers.NONE)
      .setLineColor(Color.GRAY)
      .setShowInLegend(false)
  }

}
