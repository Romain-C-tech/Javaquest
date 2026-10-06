package fr.javaquest.entity;

import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorSlot;
import fr.javaquest.equipments.weapons.Weapon;

public class Player extends Entity {
    private static final float JUMP_SPEED = 400f;
    private static final float GRAVITY = 1000f;
    private static final float GROUND_Y = 347f;


    private float velocityY = 0;
    private boolean onGround = true;

    public Player(float x, float y) {
        super(200, 5,200f , 1f,null, x, y);
    }

    public void moveLeft(float delta)  { hitbox.x -= movementSpeed * delta; isFacingRight = false;}
    public void moveRight(float delta) { hitbox.x += movementSpeed * delta; isFacingRight = true;}

    public void jump() {
        if (onGround) {
            onGround = false;
            velocityY = JUMP_SPEED;
        }
    }

    public void equip(Weapon newWeapon) {
        Weapon old = weapon;
        weapon = newWeapon;
    }

    public void unequip() {
        weapon = null;
    }

    public void equip(Armor armor) {
        armors.put(armor.getSlot(), armor);
    }

    public Armor unequip(ArmorSlot slot) {
        return armors.remove(slot);
    }

    public boolean isHeFacingRight() {return isFacingRight;}

    @Override
    public int getDamage() {
        return super.getDamage() + (weapon != null ? weapon.getDamage() : 0);
    }

    @Override
    public int getHp() {
        int total = super.getHp();
        for (Armor armor : armors.values()) {
            total += armor.getHp();
        }
        return total;
    }

    @Override
    public void update(float delta) {
        velocityY -= GRAVITY * delta;
        hitbox.y += velocityY * delta;

        if (hitbox.y <= GROUND_Y) {
            hitbox.y = GROUND_Y;
            velocityY = 0;
            onGround = true;
        }
    }
}