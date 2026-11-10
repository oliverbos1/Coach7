package com.oliverbos1.coach7

import scala.annotation.tailrec

object CyclistRollingOutModel {

  private val airResistanceConstant = 0.5
  private val rollingResistance = 100.0
  private val mass = 78.0
  private val startVelocity = 12.5
  private val deltaT = 0.01

  private def airResistance(velocity: Double): Double = airResistanceConstant * math.pow(velocity, 2)
  
  private def compute(): List[(Double, Double, Double)] = {

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

        val deltaDistance = newVelocity * deltaT
        val newDistance = distance + deltaDistance

        val newTime = time + deltaT

        loop(newTime, newVelocity, newDistance, result :+ (time, velocity, distance))
      }
    }

    loop(0.0, startVelocity, 0.0, List((0.0, startVelocity, 0.0)))
  }

  @main def runCyclistRollingOutModel(): Unit = {
    val chartData = CyclistRollingOutModel.compute()
    Chart.display(chartData, "Time (s)", "Velocity (m/s)", "Distance (m)")
  }
}
