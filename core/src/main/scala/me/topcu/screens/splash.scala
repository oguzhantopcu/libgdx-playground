/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 27.10.2013
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */

package me.topcu.screens

import com.badlogic.gdx.{Game, Gdx, Screen}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task

class splash extends Screen {
  var batch: SpriteBatch = null

  def dispose() = {
    batch.dispose()
  }

  def resume() = {}

  def pause() = {}

  def hide() = {}

  def show() = {
    Gdx.graphics.setVSync(true)

    batch = new SpriteBatch()

    Timer.schedule(new Task {
      def run() = Gdx.app.getApplicationListener.asInstanceOf[Game].setScreen(new main)
    }, 3)
  }

  def resize(p1: Int, p2: Int) = {}

  def render(p1: Float) = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    val font = new BitmapFont()
    font.setColor(1, 1, 1, 1)

    batch.begin()
    font.draw(batch, "JUMP!", 100, 100)
    batch.end()
  }
}
