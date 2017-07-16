package kill.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import kill.game.GameClass

/**
 * Created by sergio on 7/15/17.
 */

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(GameClass(), config)
}