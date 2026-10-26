package com.oliverbos1.coach7

import java.awt.Color
import scala.annotation.tailrec

object CyclistRollingOutModel {

  val airResistanceConstant = 0.5
  val rollingResistance = 100.0
  val mass = 78.0
  val startVelocity = 12.5
  val deltaT = 0.01

  def airResistance(velocity: Double): Double = airResistanceConstant * math.pow(velocity, 2)

  def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, velocity: Double, distance: Double,
             result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {

      if (velocity <= 0.0) {
        result
      } else {
        val resultingForce = rollingResistance + airResistance(velocity)
        val acceleration = -resultingForce / mass
        val deltaVelocity = acceleration * deltaT
        val newVelocity = velocity + deltaVelocity
        val newTime = time + deltaT

        val deltaDistance = velocity * deltaT
        val newDistance = distance + deltaDistance

        loop(newTime, newVelocity, newDistance, result :+ (time, velocity, distance))
      }
    }

    loop(0.0, startVelocity, 0.0, List((0.0, startVelocity, 0.0)))
  }

  @main def runCyclistRollingOutModel(): Unit = {
    val chartData = CyclistRollingOutModel.compute()
    val velocityChartData = chartData.map { case (times, velocities, _) =>
      (times, velocities)
    }
    val distanceChartData = chartData.map { case (times, _, distances) =>
      (times, distances)
    }
    Chart.display(values = velocityChartData, xTitle = "Time (s)", yTitle = "Velocity (m/s)", Color.BLUE)
    Chart.display(values = distanceChartData, xTitle = "Time (s)", yTitle = "Distance (m)", Color(155, 80, 255))
  }
}
