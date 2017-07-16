package kill.game.entities.weapons

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import kill.game.entities.definitions.Projectile
import kill.game.entities.definitions.Weapon
import kill.game.entities.definitions.WeaponDamageType
import kill.game.entities.weapons.attacks.DefaultProjectile
import kill.game.utils.addProjectilesToBeRendered
import kill.game.utils.gameCamera

/**
 * Created by sergio on 7/16/17.
 */

class DefaultWeapon : Weapon {
    override var pierceCount: Int = 2
    override var dmg: Float = 50f
    override var damageType: WeaponDamageType = WeaponDamageType.PROJECTILE
    override var numProjectiles : Int = 100
    override var baseAttackTime: Float = 0.1f
    var spread : Float = 0f
    val coverAngle : Float = 500f

    override fun attack(x: Float, y: Float, originX: Float, originY: Float) {
        val projectileList = Array<Projectile>()
        var v1 = gameCamera.frustum.planePoints[0]
        var v2 = gameCamera.frustum.planePoints[2]
        val max = v1.sub(v2).len()
        for (i in 1..numProjectiles) {
            val directionVector = Vector2(x - originX, y - originY).nor()
            if (numProjectiles > 1)
                spread = (coverAngle / ((numProjectiles-1) / 2)) / Math.exp(((max + Vector2(x - originX, y - originY).len()) / max).toDouble()).toFloat()

            var angle: Float = if (i % 2 == 0) 1f else -1f
            angle *= spread * (i / 2)

            directionVector.rotate(angle)
            projectileList.add(DefaultProjectile(dmg, originX, originY, directionVector, pierceCount))
            addProjectilesToBeRendered(projectileList)
        }
    }
}
