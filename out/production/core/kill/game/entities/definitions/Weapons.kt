package kill.game.entities.definitions

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2

/**
 * Created by sergio on 7/16/17.
 */
interface Weapon {
    var dmg: Float
    var damageType : WeaponDamageType
    var numProjectiles : Int
    var baseAttackTime : Float
    var pierceCount : Int

    fun attack(x: Float, y: Float, originX: Float, originY: Float)
}

enum class WeaponDamageType {
    PROJECTILE, MELEE, MAGIC
}

interface Melee

interface Magic

interface Projectile {
    var life : Int
    var dmg: Float
    var x : Float
    var y : Float
    var direction : Vector2
    var boundingBox: Circle
    fun tick()
    fun draw()
}