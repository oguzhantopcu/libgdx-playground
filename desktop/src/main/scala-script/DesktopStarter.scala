import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import me.topcu.JumpGame

/**
 * Created with IntelliJ IDEA.
 * User: Oğuzhan
 * Date: 26.10.2013
 * Time: 07:59
 * To change this template use File | Settings | File Templates.
 */

System.setProperty("user.name", "Q")

val cfg = new LwjglApplicationConfiguration()
cfg.title = "oç"
cfg.useGL20 = true
cfg.width = 600
cfg.height = 600
cfg.resizable = false

new LwjglApplication(new JumpGame(), cfg)