package me.topcu.utilities

import com.badlogic.gdx.math.{Vector2, MathUtils}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 21:58
 * To change this template use File | Settings | File Templates.
 */
class StageGenerator(environment: Body, difficultyFactor: Float) {
  var lastGeneratedPos: Vector2 = null

  var minGap: Float = 0
  var maxGap: Float = 0

  var y: Float = 0

  initialize()

  def initialize() {
    minGap = difficultyFactor / 2
    maxGap = difficultyFactor * 2
  }

  def generate(topEdge: Float) {
    if (y + MathUtils.random(minGap, maxGap) > topEdge) return
    y = topEdge

    val shape = new PolygonShape
    shape.setAsBox(30, 3)
    val bodyDef = new BodyDef()
    bodyDef.position.set(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth), y))
    bodyDef.`type` = BodyType.StaticBody;
    bodyDef.
    //    fixtureDef.
    //    shape.setPosition(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth), y))

    environment.createFixture(shape, 0)

    shape.dispose
  }
}
