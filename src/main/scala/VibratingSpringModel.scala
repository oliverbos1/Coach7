package com.oliverbos1.coach7

import java.awt.Color
import scala.annotation.tailrec

object VibratingSpringModel {

  val startVelocity = 0.0
  val springConstant = 2.0
  val startDisplacement = 0.0005
  val deltaT = 0.01
  val mass = 0.010

  def springForce(displacement: Double): Double = -springConstant * displacement

  def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, displacement: Double, velocity: Double,
             result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {

      if (time >= 5.0) {
        result
      } else {
        val acceleration = springForce(displacement) / mass
        val deltaVelocity = acceleration * deltaT
        val newVelocity = velocity + deltaVelocity

        val deltaDisplacement = newVelocity * deltaT
        val newDisplacement = displacement + deltaDisplacement

        val newTime = time + deltaT

        loop(newTime, newDisplacement, newVelocity, result :+ (time, displacement, velocity))
      }
    }

    loop(0.0, startDisplacement, startVelocity, List((0.0, startDisplacement, startVelocity)))
  }

  @main def runVibratingSpringModel(): Unit = {
    val chartData = VibratingSpringModel.compute()
    val displacementChartData = chartData.map { case (times, displacements, _) =>
      (times, displacements)
    }
    val velocityChartData = chartData.map { case (times, _, velocities) =>
      (times, velocities)
    }
    Chart.display(displacementChartData, "Time (s)", "Displacement (m)", Color(155, 85, 240))
    Chart.display(velocityChartData, "Time (s)", "Velocity (m/s)", Color.BLUE)
  }
}
