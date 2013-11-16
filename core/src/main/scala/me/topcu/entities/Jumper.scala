package me.topcu.entities

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.{Input, InputAdapter}
import me.topcu.entities.JumperState.JumperState

/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
class Jumper(val world: World, val x: Float, val y: Float) extends InputAdapter with GameEntity with ContactListener with ContactFilter {
  var body: Body = null

  val width: Float = 3
  val height: Float = 4
  var power: Float = 5
  val jumpPower: Float = 1000
  var fixture: Fixture = null

  var state: JumperState = JumperState.None

  initialize()

  def initialize() {
    val bodyDef: BodyDef = new BodyDef
    bodyDef.`type` = BodyType.DynamicBody
    bodyDef.position.set(x, y)
    bodyDef.fixedRotation = true
    val shape: PolygonShape = new PolygonShape
    shape.setAsBox(width / 2, height / 2)
    val fixtureDef: FixtureDef = new FixtureDef
    fixtureDef.shape = shape
    //    fixtureDef.restitution = 0
    //    fixtureDef.friction = .8f
    //    fixtureDef.density = 3
    body = world.createBody(bodyDef)
    fixture = body.createFixture(fixtureDef)

    body.setLinearDamping(3)
  }

  def update() {
    val velocity = body.getLinearVelocity
    System.err.println("1" + state + velocity.x + ":" + velocity.y)
    state match {
      case JumperState.Left => velocity.x -= power
      case JumperState.Right => velocity.x += power
      case _ =>
    }
    if (state == JumperState.Left || state == JumperState.Right) {
      body.setLinearVelocity(velocity)
    }
    if (isJumping) {
      body.applyLinearImpulse(0, jumpPower, body.getWorldCenter.x, body.getWorldCenter.y, true)
      isJumping = false;
    }
  }

  var isJumping: Boolean = false;

  override def keyDown(keyCode: Int): Boolean = {
    keyCode match {
      //      case Input.Keys.W => velocity.y = power
      case Input.Keys.A => state = JumperState.Left
      case Input.Keys.D => state = JumperState.Right
      case Input.Keys.SPACE =>
        isJumping = true
      case _ =>
    }

    super.keyDown(keyCode)
  }


  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean ={
    if(screenX > body.getPosition.x)
      state = JumperState.Right
    else if(screenX < body.getPosition.x)
      state = JumperState.Left

    super.touchDown(screenX, screenY, pointer, button)
  }
  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    if (state == JumperState.Left || state == JumperState.Right) {
      state = JumperState.None
    }

    super.touchUp(screenX, screenY, pointer, button)
  }

  override def keyUp(keyCode: Int): Boolean = {
    if (state == JumperState.Left || state == JumperState.Right) {
      if (keyCode == Input.Keys.A || keyCode == Input.Keys.D) state = JumperState.None
    }

    super.keyDown(keyCode)
  }

  def beginContact(contact: Contact) = {  }

  def endContact(p1: Contact) = { }

  def shouldCollide(fixtureA: Fixture, fixtureB: Fixture): Boolean = {
    if (fixtureA == fixture || fixtureB == fixture)
      isJumping = true
    false
  }

  def preSolve(contact: Contact, oldManifold: Manifold) {  }

  def postSolve(contact: Contact, impulse: ContactImpulse) {  }
}

object JumperState extends Enumeration {
  type JumperState = Value
  val None, Left, Right, Jump, Jumping = Value
}

