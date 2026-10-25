package com.oliverbos1.coach7

import org.knowm.xchart.{XYChartBuilder, SwingWrapper}
import scala.annotation.tailrec

object VolumeModel {

  val initialVolume = 10.0
  val decreasePerStep = 1.0
  val deltaT = 1.0

  def simulate(): Unit =

    @tailrec
    def loop(t: Double, v: Double, times: List[Double], volumes: List[Double]): (List[Double], List[Double]) = {

      if (v <= 0) {
        (times.reverse, volumes.reverse)
      } else if (times.length > 100) {
        (times, volumes)
      } else {
        val nextT = t + deltaT
        val nextV = v - decreasePerStep

        loop(nextT, nextV, nextT :: times, nextV :: volumes)
      }
    }

    val (time, volume) =
      loop(0.0, initialVolume, List(0.0), List(initialVolume))

    val chart = new XYChartBuilder()
      .width(800)
      .height(600)
      .title("Volume over Time")
      .xAxisTitle("Time (t)")
      .yAxisTitle("Volume (v)")
      .build()

    chart.addSeries("Volume", time.toArray, volume.toArray)

    new SwingWrapper(chart).displayChart()

}
@main def runVolumeModel(): Unit =
  VolumeModel.simulate()
