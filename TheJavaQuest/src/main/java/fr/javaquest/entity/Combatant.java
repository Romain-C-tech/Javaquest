package fr.javaquest.entity;

import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.entity.equipments.weapons.Weapon;

public interface Combatant {
    int getMaxHp();
    int getHp();
    int getDamage();
    float getMovementSpeed();
    Rectangle getHitbox();
    float getAttackSpeed();

    Weapon getWeapon();

    void takeDamage(int amount);
}
