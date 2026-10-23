package com.oliverbos1.coach7

import org.knowm.xchart.{XYChartBuilder, SwingWrapper}

object VolumeModel extends App {

  // Model parameters
  val initialVolume = 100.0
  val decreasePerStep = 2.0
  val deltaT = 0.5
  val steps = 50

  // Simulate the model
  val time = (0 to steps).map(i => i * deltaT).toArray

  val volume = (0 to steps).map { i =>
    math.max(0.0, initialVolume - i * decreasePerStep)
  }.toArray

  // Create the graph
  val chart = new XYChartBuilder()
    .width(800)
    .height(600)
    .title("Volume over Time")
    .xAxisTitle("Time (t)")
    .yAxisTitle("Volume (v)")
    .build()

  // Add the simulation results
  chart.addSeries("Volume", time, volume)

  // Display the graph
  new SwingWrapper(chart).displayChart()
}
