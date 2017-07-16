package kill.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import kill.game.entities.definitions.Drawable
import kill.game.entities.definitions.Projectile
import kill.game.entities.definitions.Tickable
import kill.game.entities.enemies.Enemy

/**
 * Created by sergio on 7/15/17.
 */

val w = Gdx.graphics.width
val h = Gdx.graphics.height

val gameCamera: OrthographicCamera = OrthographicCamera()
var renderer : ShapeRenderer = ShapeRenderer()

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
var attack : Array<Projectile> = Array()
fun addProjectilesToBeRendered(projectileList: Array<Projectile>) {
    while (projectileList.size > 0) {
        val proj: Projectile = projectileList.pop()
        toRenderObjects.add(proj)
        attack.add(proj)
    }
}


var enemies : Array<Enemy> = Array()
fun addEnemiesToBeRandered(enemy: Enemy) {
    toRenderObjects.add(enemy)
    enemies.add(enemy)
}

fun renderObjects() {

    val enemiesIterator = enemies.iterator()
    print(enemies.size.toString() + "\n")
    while (enemiesIterator.hasNext()) {
        val enemy = enemiesIterator.next()
        if (attack.size == 0)
            break
        val projectileIterator = attack.iterator()
        while (projectileIterator.hasNext()) {
            val projectile = projectileIterator.next()
            if (enemy.hits.contains(projectile))
                continue
            if (Rectangle(projectile.x - 3, projectile.y - 3, 6f, 6f).overlaps(enemy.boundingBox)) {
                projectile.life--
                if (projectile.life == 0) {
                    projectileIterator.remove()
                    toRenderObjects.removeValue(projectile, false)
                }
                enemy.hp -= projectile.dmg
                enemy.hits.add(projectile)
                if (enemy.hp <= 0) {
                    enemiesIterator.remove()
                    toRenderObjects.removeValue(enemy, false)
                    break
                }
            }
        }
    }

    val iterator = toRenderObjects.iterator()
    while (iterator.hasNext()) {
        val obj = iterator.next()
        if (obj is Projectile) {
            obj.tick()
            if (isProjectileOutOfBounds(obj)) {
                iterator.remove()
                attack.removeValue(obj, false)
            }
            else
                obj.draw()
        }
        else if (obj is Drawable) {
            (obj as? Tickable)?.tick()
            obj.draw()
        }
    }
}

fun isProjectileOutOfBounds(projectile: Projectile): Boolean {
    var n = Vector3(projectile.x, projectile.y, 0f)
    n = gameCamera.project(n)
    return n.x !in 0 .. Gdx.graphics.width || n.y !in 0 .. Gdx.graphics.height
}