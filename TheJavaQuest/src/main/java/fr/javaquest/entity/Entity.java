package fr.javaquest.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {

    protected int hp;
    protected float movementSpeed; // en pixels par seconde
    protected Rectangle hitbox;

    public Entity(int hp, float movementSpeed, float x, float y) {
        this.hp = hp;
        this.movementSpeed = movementSpeed;
        this.hitbox = new Rectangle(x, y, 10, 10);
    }

    public int getHp() { return hp; }
    public float getMovementSpeed() { return movementSpeed; }
    public Rectangle getHitbox() { return hitbox; }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            System.out.println("Player is dead");
        }
    }

    public void keepInside(float mapWidth, float mapHeight) {
        hitbox.x = MathUtils.clamp(hitbox.x, 0, mapWidth - hitbox.width);
        hitbox.y = MathUtils.clamp(hitbox.y, 0, mapHeight - hitbox.height);
    }

    public void render(ShapeRenderer shapes) {
        shapes.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public abstract void update(float delta);
}