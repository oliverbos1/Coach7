package com.oliverbos1.coach7

import scala.annotation.tailrec

object VibratingSpringModel {

  private val startVelocity = 0.0
  private val springConstant = 0.5
  private val airResistanceConstant = 10.0
  private val startDisplacement = 0.0005
  private val deltaT = 0.01
  private val mass = 0.010

  private val airResistanceEnabled = true
  private def springForce(displacement: Double): Double = -springConstant * displacement
  private def airResistance(velocity: Double): Double = airResistanceConstant * velocity * velocity.abs

  private def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, displacement: Double, velocity: Double,
             result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {

      if (time >= 10.0) {
        result
      } else {
        val resultingForce = springForce(displacement) - (if (airResistanceEnabled) airResistance(velocity) else 0.0)
        val acceleration = resultingForce / mass
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
    Chart.display(chartData, "Time (s)", "Displacement (m)", "Velocity (m/s)")
  }
}
