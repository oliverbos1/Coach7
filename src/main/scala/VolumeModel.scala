package com.oliverbos1.coach7

import org.knowm.xchart.{XYChartBuilder, SwingWrapper}
import scala.annotation.tailrec

object VolumeModel {

  val initialVolume = 10.0
  val decreasePerStep = 1.0
  val deltaT = 1.0

  def compute(): List[(Double, Double)] = {

    @tailrec
    def loop(t: Double, v: Double, result: List[(Double, Double)]): List[(Double, Double)] = {

      if (v <= 0) {
        result
      } else {
        val nextT = t + deltaT
        val nextV = v - decreasePerStep

        loop(nextT, nextV, result :+ (nextT, nextV))
      }
    }

    loop(0.0, initialVolume, List((0.0, initialVolume)))
  }

  @main def runVolumeModel(): Unit = {
    val chartData = VolumeModel.compute()
    Chart.display(values = chartData, xTitle = "Time (t)", yTitle = "Volume (v)")
  }
}
