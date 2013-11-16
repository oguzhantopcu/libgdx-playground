/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
package me.topcu.utilities

object GameConfig extends TGameConfig {
  def timeStep = 1 / 60f

  def velocityIterations = 8

  def positionIterations = 3

  def gravity = -100f
}
