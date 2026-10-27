package com.oliverbos1.coach7

import java.awt.Color
import scala.annotation.tailrec

object VibratingSpringModel {

  val startVelocity = 0.0
  val springConstant = 0.5
  val airResistanceConstant = 10.0
  val startDisplacement = 0.0005
  val deltaT = 0.01
  val mass = 0.010

  def springForce(displacement: Double): Double = -springConstant * displacement

  def airResistance(velocity: Double): Double = airResistanceConstant * velocity * velocity.abs

  def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, displacement: Double, velocity: Double,
             result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {

      if (time >= 10.0) {
        result
      } else {
        val resultingForce = springForce(displacement) - airResistance(velocity)
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
