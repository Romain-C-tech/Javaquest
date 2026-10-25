package fr.javaquest.entity;

import com.badlogic.gdx.math.Rectangle;

/**
 * Tout les combattants ont des points de vie, des points de vie maximum, ont des points de dégats,
 * ont une vitesse de déplacement, ont une zone de reception de dégats, peuvent recevoir des dégats.
 *
 * @author Romain-C-tech
 */
public interface Fighter {
    int getMaxHp();
    int getHp();
    int getDamage();
    float getMovementSpeed();
    Rectangle getHitbox();
    void takeDamage(int amount);
}
