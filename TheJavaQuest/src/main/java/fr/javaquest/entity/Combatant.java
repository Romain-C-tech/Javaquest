package fr.javaquest.entity;

import com.badlogic.gdx.math.Rectangle;

public interface Combatant {
    int getHp();
    int getDamage();
    float getMovementSpeed();
    float getAttackSpeed();
    Rectangle getHitbox();
    void takeDamage(int amount);
}
