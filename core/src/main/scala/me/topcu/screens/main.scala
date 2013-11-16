/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */

package me.topcu.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.{OrthographicCamera, GL20}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.{Vector3, Vector2}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import me.topcu.entities.Jumper
import me.topcu.utilities.{StageGenerator, GameConfig}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

class main extends Screen {
  var player: Jumper = null
  var world: World = null

  var renderer: Box2DDebugRenderer = null
  var camera: OrthographicCamera = null

  var batch: SpriteBatch = null

  def dispose() = {}

  def resume() = {}

  def pause() = {}

  def hide() = {}

  var stageGenerator: StageGenerator = null

  var bottomLeft: Vector3 = null
  var bottomRight: Vector3 = null

  def show() = {
    world = new World(new Vector2(0, GameConfig.gravity), true)
    player = new Jumper(world, 0, 0)

    renderer = new Box2DDebugRenderer
    camera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)

    batch = new SpriteBatch()

    bottomLeft = new Vector3(0, Gdx.graphics.getHeight, 0)
    bottomRight = new Vector3(Gdx.graphics.getWidth, bottomLeft.y, 0)

    camera.position.x = 0

    world.setContactFilter(player)
    world.setContactListener(player)

    Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter {
      override def scrolled(amount: Int): Boolean = {
        camera.zoom += amount / 25f
        true
      }
    }, player))

    val bodyDef: BodyDef = new BodyDef
    val fixtureDef: FixtureDef = new FixtureDef

    bodyDef.`type` = BodyType.StaticBody
    bodyDef.position.set(0, 0)
    // ground shape
    val groundShape: ChainShape = new ChainShape
    groundShape.createChain(Array[Float](bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y))
    fixtureDef.shape = groundShape
    bodyDef.position.set(0f,0f)
    val ground = world.createBody(bodyDef)

    ground.createFixture(fixtureDef)
    groundShape.dispose()

    stageGenerator = new StageGenerator(ground, 10)
  }

  def resize(width: Int, height: Int) {
    camera.viewportWidth = width
    camera.viewportHeight = height
  }

  def render(p1: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    player.update()

    if (player.body.getPosition.x < 0)
    {
      player.body.setTransform(bottomRight.x -20, player.body.getPosition.y, player.body.getAngle)
    }
    else if (player.body.getPosition.x > bottomRight.x)
      player.body.setTransform(bottomLeft.x, player.body.getPosition.y, player.body.getAngle)

    camera.position.y = if (player.body.getPosition.y > camera.position.y) player.body.getPosition.y else camera.position.y
    camera.position.x = player.body.getPosition.x
    camera.update()

    batch.setProjectionMatrix(camera.combined)

    stageGenerator.generate(camera.position.y + camera.viewportHeight / 2)

    world.step(GameConfig.timeStep, GameConfig.velocityIterations, GameConfig.positionIterations)

    renderer.render(world, camera.combined)
  }
}
