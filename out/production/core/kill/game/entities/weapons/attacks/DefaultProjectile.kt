package kill.game.entities.weapons.attacks

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import kill.game.entities.definitions.Projectile

/**
 * Created by sergio on 7/16/17.
 */

class DefaultProjectile(override var dps: Float, override var x: Float, override var y: Float, override var direction: Vector2) : Projectile {
    override var boundingBox: Circle = Circle(x, y, 3f)
    var projectionMatrixSet : Boolean = false
    var renderer : ShapeRenderer = ShapeRenderer()
    override fun draw(batch: SpriteBatch) {
        tick()
        if (!projectionMatrixSet) {
            renderer.projectionMatrix = batch.projectionMatrix
            projectionMatrixSet = true
        }
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.WHITE

        renderer.circle(boundingBox.x, boundingBox.y, boundingBox.radius)
        renderer.end()
    }

    var projectileSpeed: Float = 150f

    override fun tick() {
        boundingBox.x += direction.x * projectileSpeed * Gdx.graphics.deltaTime
        boundingBox.y += direction.y * projectileSpeed * Gdx.graphics.deltaTime
        x = boundingBox.x
        y = boundingBox.y
    }
}