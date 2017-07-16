package kill.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import kill.game.entities.definitions.Projectile

/**
 * Created by sergio on 7/15/17.
 */

val w = Gdx.graphics.width
val h = Gdx.graphics.height

val gameCamera: OrthographicCamera = OrthographicCamera(1000f, 1000f * (h.toFloat()/w.toFloat()))

fun clearToRGBA(red: Int = 0, blue: Int = 0, green: Int = 0, alpha: Int = 255) {
    val r: Float = if (red !in 0..255) 0f else red.toFloat()/255
    val g: Float = if (green !in 0..255) 0f else green.toFloat()/255
    val b: Float = if (blue !in 0..255) 0f else blue.toFloat()/255
    val a: Float = if (alpha !in 0..255) 0f else alpha.toFloat()/255

    Gdx.gl.glClearColor(r, g, b, a)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.app.debug("clearToRGBA", "Cleared to color " + r + g + b)
}

var toRenderObjects : Array<Any> = Array()
fun addProjectilesToBeRendered(projectileList: Array<Projectile>) {
    while (projectileList.size > 0)
        toRenderObjects.add(projectileList.pop())
}

fun renderObjects(batch: SpriteBatch) {
    val iterator = toRenderObjects.iterator()
    while (iterator.hasNext()) {
        val obj = iterator.next()
        if (obj is Projectile) {
            obj.tick()
            if (isProjectileOutOfBounds(obj))
                iterator.remove()
            else
                obj.draw(batch)
        }
    }
}

fun isProjectileOutOfBounds(projectile: Projectile): Boolean {
    var n = Vector3(projectile.x, projectile.y, 0f)
    n = gameCamera.project(n)
    return n.x !in 0 .. Gdx.graphics.width || n.y !in 0 .. Gdx.graphics.height
}