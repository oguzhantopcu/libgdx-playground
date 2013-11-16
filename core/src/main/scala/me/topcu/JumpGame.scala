/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 26.10.2013
 * Time: 02:46
 * To change this template use File | Settings | File Templates.
 */

package me.topcu

import com.badlogic.gdx.Game
import java.text.Normalizer
import me.topcu.screens.main
import com.badlogic.gdx.graphics.{GLTexture}

class JumpGame extends Game {
  def create() = {
    normalizeUserName() //bug workaround
    GLTexture.setEnforcePotImages(false)

    setScreen(new main())
  }

  def normalizeUserName() {
    val userName = System.getProperty("user.name")
    Normalizer.normalize(userName, Normalizer.Form.NFC)
      .replaceAll("[^\\p{ASCII}]", "")
    System.setProperty("user.name", userName)
  }
}