package me.topcu.utilities

/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
trait TGameConfig {
  def timeStep: Float

  def velocityIterations: Integer

  def positionIterations: Integer

  def gravity: Float
}
