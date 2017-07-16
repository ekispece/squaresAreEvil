package kill.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import kill.game.entities.BoxPlayer
import kill.game.utils.gameCamera
import kill.game.utils.clearToRGBA
import kill.game.utils.renderObjects

/**
 * Created by sergio on 7/15/17.
 */

class GameClass : ApplicationAdapter() {
    lateinit var actor : Rectangle
    lateinit var inputMultiplexer : InputMultiplexer
    lateinit var player : BoxPlayer

    override fun create() {
        actor = Rectangle()
        player = BoxPlayer(10f, 10f, 30f)
        inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(player.getInputProcessor())
        Gdx.input.inputProcessor = inputMultiplexer
    }

    override fun render() {
        clearToRGBA(green = 170, red = 45, blue = 45)

        gameCamera.update()

        val batch = SpriteBatch()
        batch.projectionMatrix = gameCamera.combined

        batch.begin()
        batch.end()
        renderObjects(batch)
        player.draw(batch)

    }
}