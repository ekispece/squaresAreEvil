package kill.game.entities.enemies

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import kill.game.entities.definitions.Drawable
import kill.game.entities.definitions.Projectile
import kill.game.entities.definitions.Tickable
import kill.game.utils.PLAYER
import kill.game.utils.addEnemiesToBeRandered
import kill.game.utils.gameCamera
import kill.game.utils.renderer
import com.badlogic.gdx.utils.Array

/**
 * Created by sergio on 7/16/17.
 */
interface Enemy {
    var hp : Float
    var position : Vector3
    var boundingBox : Rectangle
    var hits : Array<Projectile>
}


var lastTimeEnemySpawned: Long = 0
fun enemySpawner(frequency: Float) {
    if (lastTimeEnemySpawned + TimeUtils.millisToNanos(((1f / frequency) * 1000).toLong()) < TimeUtils.nanoTime()) {
        spawnNewEnemy()
        lastTimeEnemySpawned = TimeUtils.nanoTime()
    }
}

fun spawnNewEnemy() {
    var enemyCoords = Vector3(0f, 0f, 0f)
    val face = MathUtils.random.nextInt(4)

    when (face) {
        0 -> {
            enemyCoords.x = Gdx.graphics.width.toFloat()
            enemyCoords.y = MathUtils.random.nextInt(Gdx.graphics.height).toFloat()
        }
        1 -> {
            enemyCoords.x = 0f
            enemyCoords.y = MathUtils.random.nextInt(Gdx.graphics.height).toFloat()
        }
        2 -> {
            enemyCoords.x = MathUtils.random.nextInt(Gdx.graphics.width).toFloat()
            enemyCoords.y = Gdx.graphics.height.toFloat()
        }
        3 -> {
            enemyCoords.x = MathUtils.random.nextInt(Gdx.graphics.width).toFloat()
            enemyCoords.y = 0f
        }
    }
    enemyCoords = gameCamera.unproject(enemyCoords)

    addEnemiesToBeRandered(object : Enemy, Drawable, Tickable {
        override var hits: Array<Projectile> = Array()
        override var boundingBox: Rectangle = Rectangle()
        override var moveSpeed: Float = 70f
        override fun tick() {
            var direction : Vector3 = Vector3(PLAYER.x - position.x, PLAYER.y - position.y, 0f).nor()

            this.position.x += direction.x * moveSpeed * Gdx.graphics.deltaTime
            this.position.y += direction.y * moveSpeed * Gdx.graphics.deltaTime
        }

        override fun draw() {
            renderer.begin(ShapeRenderer.ShapeType.Filled)

            renderer.color = Color.CORAL
            renderer.rect(this.position.x, this.position.y, 10f, 10f)
            this.boundingBox.x = this.position.x
            this.boundingBox.y = this.position.y
            this.boundingBox.height = 10f
            this.boundingBox.width = 10f

            renderer.end()

        }

        override var hp : Float = 10f
        override var position : Vector3 = enemyCoords
    })

}