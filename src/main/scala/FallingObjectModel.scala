package com.oliverbos1.coach7

import scala.annotation.tailrec

object FallingObjectModel {

  val acceleration = 9.81
  val deltaT = 0.01

  def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, velocity: Double, distance: Double, result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {
      if (time >= 5.0) {
        result
      } else {
        val newVelocity = velocity + (acceleration * deltaT)
        val newDistance = distance + (newVelocity * deltaT)
        val newTime = time + deltaT

        loop(newTime, newVelocity, newDistance, result :+ (time, velocity, distance))
      }
    }

    loop(0.0, 0.0, 0.0, List((0.0, 0.0, 0.0)))
  }

  @main def runaFallingObjectModel(): Unit = {
    val chartData = FallingObjectModel.compute()
    Chart.display(chartData, "Time (s)", "Velocity (m/s)", "Distance (m)")
  }
}
