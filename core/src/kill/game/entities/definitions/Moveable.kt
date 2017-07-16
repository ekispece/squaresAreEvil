package kill.game.entities.definitions

import com.badlogic.gdx.Gdx

/**
 * Created by sergio on 7/15/17.
 */

abstract class Moveable {

    abstract var x : Float
    abstract var y : Float
    abstract var moveSpeed : Float
    var moveLeft : Boolean = false
    var moveRight : Boolean = false
    var moveUp : Boolean = false
    var moveDown : Boolean = false

    fun moveLeft(mov : Boolean = true) {
        moveRight = if (moveRight && mov) false else moveRight
        moveLeft = mov
    }

    fun moveRight(mov : Boolean = true) {
        moveLeft = if (moveLeft && mov) false else moveLeft
        moveRight = mov
    }

    fun moveUp(mov : Boolean = true) {
        moveDown = if (moveDown && mov) false else moveDown
        moveUp = mov
    }

    fun moveDown(mov : Boolean = true) {
        moveUp = if (moveUp && mov) false else moveUp
        moveDown = mov
    }

    fun updatePosition() {
        if (moveLeft) {
            x -= moveSpeed * Gdx.graphics.deltaTime
        }
        if (moveRight) {
            x += moveSpeed * Gdx.graphics.deltaTime
        }
        if (moveUp) {
            y += moveSpeed * Gdx.graphics.deltaTime
        }
        if (moveDown) {
            y -= moveSpeed * Gdx.graphics.deltaTime
        }
    }
}