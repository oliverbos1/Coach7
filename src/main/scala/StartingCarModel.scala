package com.oliverbos1.coach7

import scala.annotation.tailrec

object StartingCarModel {

  private val mass = 1000
  private val airResistanceConstant = 0.26
  private val startVelocity = 0.0
  private val deltaT = 0.01
  private val motorForce = 2500

  private def airResistance(velocity: Double): Double = airResistanceConstant * math.pow(velocity, 2)
  
  private def compute(): List[(Double, Double, Double)] = {

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
