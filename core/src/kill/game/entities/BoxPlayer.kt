package kill.game.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import kill.game.entities.definitions.Moveable
import kill.game.entities.definitions.Weapon
import kill.game.entities.weapons.DefaultWeapon
import kill.game.utils.gameCamera

/**
 * Created by sergio on 7/15/17.
 */

class BoxPlayer(override var x: Float = 0f, override var y: Float = 0f, var size: Float = 0f) : Moveable() {
    var renderer : ShapeRenderer = ShapeRenderer()
    var projectionMatrixSet : Boolean = false
    override var moveSpeed : Float = 100f
    var weapon : Weapon = DefaultWeapon()
    var isAttacking : Boolean = false
    val baseAttackSpeed = 1.6f
    var attackSpeed = 1f
    var lastAttackTime : Long = 0

    fun draw(batch: Batch) {
        tick()
        if (!projectionMatrixSet) {
            renderer.projectionMatrix = batch.projectionMatrix
            projectionMatrixSet = true
        }
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.BLUE

        val x1 = x - size / 2
        val y1 = y
        val x2 = x + size / 2
        val y2 = y
        val x3 = x
        val y3 = y + size

        renderer.triangle(x1, y1, x2, y2, x3, y3)
        renderer.end()
    }

    fun tick() {
        updatePosition()
        if (isAttacking)
            attack(Gdx.input.x, Gdx.input.y)
    }

    fun attack(eventX: Int, eventY: Int) {
        val now = TimeUtils.nanoTime()
        if (now - lastAttackTime < TimeUtils.millisToNanos(((baseAttackSpeed/attackSpeed) * 1000).toLong()))
            return
        val clickVec : Vector3 = Vector3()
        gameCamera.unproject(clickVec.set(eventX.toFloat(), eventY.toFloat(), 0f))
        weapon.attack(clickVec.x, clickVec.y, x, y)
        lastAttackTime = TimeUtils.nanoTime()
    }

    fun getInputProcessor() = object : InputAdapter() {
        override fun keyDown(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.A -> moveLeft()
                Input.Keys.D -> moveRight()
                Input.Keys.W -> moveUp()
                Input.Keys.S -> moveDown()
            }
            return true
        }

        override fun keyUp(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.A -> moveLeft(false)
                Input.Keys.D -> moveRight(false)
                Input.Keys.W -> moveUp(false)
                Input.Keys.S -> moveDown(false)
            }
            return true
        }

        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            when (button) {
                Input.Buttons.LEFT -> {
                    isAttacking = true
                    attack(screenX, screenY)
                }
            }
            return true
        }

        override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            if (isAttacking) {
                isAttacking = false
                return true
            }
            return false
        }
    }

}