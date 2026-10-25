package fr.javaquest.entity.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Cette interface permet de voir les différentes annimations de Sprite que chaque ennemies possèdent,
 * ainsi que des caractéristiques de positions du Sprite.
 * @author ssamepi
 */
public interface EnemyRenderer {
    void update(float delta);
    void draw(SpriteBatch batch);
    void dispose();

    void setIdle();
    void setRun();
    void setAttack();
    void setHit();
    void setDeath();

    void setPosition(float x, float y);
    void setFacingRight(boolean facingRight);
    float getDeathDuration();
}
