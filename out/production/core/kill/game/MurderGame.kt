package kill.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kill.game.entities.BoxPlayer
import kill.game.entities.enemies.enemySpawner
import kill.game.utils.*

/**
 * Created by sergio on 7/15/17.
 */

class GameClass : ApplicationAdapter() {
    lateinit var inputMultiplexer : InputMultiplexer
    lateinit var player : BoxPlayer
    var baseFreq = 0.01f

    override fun create() {
        player = BoxPlayer(0f, 0f, 20f)
        inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(player.getInputProcessor())
        Gdx.input.inputProcessor = inputMultiplexer
        gameCamera.setToOrtho(false, 1000f, 1000f * (h.toFloat()/ w.toFloat()))
        PLAYER = player
    }

    override fun render() {
        clearToRGBA(green = 170, red = 45, blue = 45)

        gameCamera.update()

        val batch = SpriteBatch()
        batch.projectionMatrix = gameCamera.combined
        renderer.projectionMatrix = batch.projectionMatrix

        batch.begin()
        batch.end()
        renderObjects()
        player.draw()

        enemySpawner(baseFreq)
        baseFreq += Gdx.graphics.deltaTime * baseFreq * .5f

    }
}