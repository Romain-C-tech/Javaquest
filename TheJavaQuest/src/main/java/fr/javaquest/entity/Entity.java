package fr.javaquest.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorSlot;
import fr.javaquest.equipments.weapons.Weapon;

import java.util.EnumMap;
import java.util.Map;

public abstract class Entity implements Combatant {

    protected int hp;
    protected int damage;
    protected float movementSpeed;
    protected float attackSpeed;
    protected Weapon weapon;
    protected boolean isFacingRight;
    protected final Map<ArmorSlot, Armor> armors = new EnumMap<>(ArmorSlot.class);
    protected Rectangle hitbox;

    public Entity(int hp, int damage, float movementSpeed, float attackSpeed, Weapon weapon, float x, float y) {
        this.hp = hp;
        this.damage = damage;
        this.movementSpeed = movementSpeed;
        this.attackSpeed = attackSpeed;
        this.weapon = weapon;
        this.hitbox = new Rectangle(x, y, 10, 10);
    }

    @Override public int getHp() {return hp;}
    @Override public int getDamage() {return damage;}
    @Override public float getMovementSpeed() {return movementSpeed;}
    @Override public float getAttackSpeed() {return attackSpeed;}
    @Override public Rectangle getHitbox() {return hitbox;}

    @Override
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

    public abstract void update(float delta);
}
