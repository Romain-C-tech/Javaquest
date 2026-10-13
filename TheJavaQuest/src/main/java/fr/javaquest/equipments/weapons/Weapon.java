package fr.javaquest.equipments.weapons;

import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.equipments.Equipment;
import fr.javaquest.equipments.Rarity;

import java.awt.*;

public class Weapon  implements Equipment {
    private final WeaponData data;
    private final Rarity rarity;

    public Weapon(WeaponData data, Rarity rarity) {
        this.data = data;
        this.rarity = rarity;
    }

    public com.badlogic.gdx.math.Rectangle getAttackHitbox(Rectangle owner, boolean facingRight) {
        HitboxData h = data.hitbox;
        float x = facingRight ? owner.x + owner.width : owner.x - h.width;
        float y = owner.y + h.offsetY;
        return new com.badlogic.gdx.math.Rectangle((int) x, (int) y, (int) h.width, (int) h.height);
    }

    public boolean isRanged() { return data.range > 0; }
    public float getRange()   { return data.range; }

    public int getDamage() {return Math.round(data.damage * rarity.multiplier);}
    public float getAttackSpeed() {return data.attackSpeed;}

    public Rarity getRarity() {return rarity;}
    public String getName() {return data.name;}
}
