package fr.javaquest.entity;

import com.badlogic.gdx.math.Rectangle;

public interface Combatant {
    int getMaxHp();
    int getHp();
    int getDamage();
    float getMovementSpeed();
    Rectangle getHitbox();
    void takeDamage(int amount);
}
