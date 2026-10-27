package com.oliverbos1.coach7

import java.awt.Color
import scala.annotation.tailrec

object StartingCarModel {

  val mass = 1000
  val airResistanceConstant = 0.26
  val startVelocity = 0.0
  val deltaT = 0.01
  val motorForce = 2500

  def airResistance(velocity: Double): Double = airResistanceConstant * math.pow(velocity, 2)

  def compute(): List[(Double, Double, Double)] = {

    @tailrec
    def loop(time: Double, velocity: Double, distance: Double,
             result: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {

      if (time >= 10.0) {
        result
      } else {
        val resultingForce = motorForce - airResistance(velocity)
        val acceleration = resultingForce / mass

        val deltaVelocity = acceleration * deltaT
        val newVelocity = velocity + deltaVelocity

        val deltaDistance = newVelocity * deltaT
        val newDistance = distance + deltaDistance

        val newTime = time + deltaT

        loop(newTime, newVelocity, newDistance, result :+ (time, velocity, distance))
      }
    }

    loop(0.0, startVelocity, 0.0, List((0.0, startVelocity, 0.0)))
  }

  @main def runStartingCarModel(): Unit = {
    val chartData = StartingCarModel.compute()
    Chart.display(chartData, "Time(s)", "Velocity (m/s)", "Distance (m)")
  }
}
