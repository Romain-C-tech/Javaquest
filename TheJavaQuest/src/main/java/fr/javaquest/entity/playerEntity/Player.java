package fr.javaquest.entity.playerEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.entity.Entity;
import com.badlogic.gdx.utils.Array;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.equipments.weapons.WeaponRenderer;
import fr.javaquest.entity.equipments.armors.Armor;
import fr.javaquest.entity.equipments.armors.ArmorSlot;
import fr.javaquest.entity.equipments.weapons.Weapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class Player extends Entity {

    private static final float JUMP_SPEED = 400f;

    private LayeredPlayer visual;
    private float attackCooldown = 0f;
    private float velocityY = 0;
    private boolean onGround = true;
    private WeaponRenderer weaponRenderer;

    private final Array<Texture> textures = new Array<>();
    private MapViewer mapViewer;

    public Player(float x, float y, MapViewer mapViewer) {
        super(200,0 ,200f,1F,null, x, y);
        this.mapViewer = mapViewer;
    }
    public void restoreLife(int restorehp){
        if (this.hp < getMaxHp()){
            if ((getMaxHp() - this.hp) < restorehp){
                this.hp = getMaxHp();
            }
            else {
                this.hp += restorehp;
            }
        }
    }

    private boolean canMoveTo(float nextX, float nextY){
        Rectangle nextHitbox = new Rectangle(nextX, nextY, hitbox.width, hitbox.height);
        return !mapViewer.collides(nextHitbox);
    }

    public void moveLeft(float delta) {
        float nextX = hitbox.x - movementSpeed * delta;
        if (canMoveTo(nextX, hitbox.y)){
            hitbox.x = nextX;
        }

        getVisual().facingRight = false;
        getVisual().setRun();
    }

    public void moveRight(float delta) {
        float nextX = hitbox.x + movementSpeed * delta;
        if (canMoveTo(nextX, hitbox.y)) {
            hitbox.x = nextX;
        }
        getVisual().facingRight = true;
        getVisual().setRun();
    }

    public void jump() {
        if (onGround) {
            onGround = false;
            velocityY = JUMP_SPEED;
        }
    }

    public void attack(List<? extends Entity> targets) {
        if (!canAttack()) return;
        getVisual().startAttack();

        attackCooldown = 1f / weapon.getAttackSpeed();

        Rectangle zone = weapon.getAttackHitbox(hitbox, getVisual().facingRight);

        for (Entity target : targets) {
            if (zone.overlaps(target.getHitbox())) {
                target.takeDamage(getDamage());
                return;
            }
        }
    }

    @Override
    public void update(float delta) {
        velocityY -= GRAVITY * delta;

        float newY = hitbox.y + velocityY * delta;

        Rectangle futureHitbox = new Rectangle(
            hitbox.x,
            newY,
            hitbox.width,
            hitbox.height
        );

        boolean collision = false;

        for (Rectangle collider : mapViewer.getCollisionsRectangles()) {

            if (futureHitbox.overlaps(collider)) {

                if (velocityY <= 0 && hitbox.y >= collider.y + collider.height - 1) {

                    hitbox.y = collider.y + collider.height;
                    velocityY = 0;
                    onGround = true;
                    collision = true;
                    break;
                }

                if (velocityY > 0 && hitbox.y + hitbox.height <= collider.y + 1) {

                    hitbox.y = collider.y - hitbox.height;
                    velocityY = 0;
                    collision = true;
                    break;
                }
            }
        }

        if (!collision) {
            hitbox.y = newY;
            onGround = false;
        }

        getVisual().update(delta);

        if (attackCooldown > 0f) attackCooldown -= delta;
    }

    public void render(SpriteBatch batch) {
        Rectangle hb = getHitbox();

        LayeredPlayer playerVisual = getVisual();
        playerVisual.x =
                hb.x + hb.width / 2f - 32 / 2f;

        playerVisual.y = hb.y;

        playerVisual.draw(batch);
    }

    public LayeredPlayer getVisual() {
        if (visual == null) {
            visual = new LayeredPlayer();
        }
        return visual;
    }

    @Override
    public int getMaxHp() {
        int total = 200;
        for (Armor armor : armors.values()) {
            total += armor.getHp();
        }
        return total;
    }

    public void unequip() {
        weapon = null;
        weaponRenderer.setWeapon();
    }
    public void equip(Weapon newWeapon) {weapon = newWeapon;}
    public void equip(Armor armor) {armors.put(armor.getSlot(), armor);}
    public Armor unequip(ArmorSlot slot) {return armors.remove(slot);}
    public void idle(){ getVisual().setIdle(); }


    public boolean canAttack() {return attackCooldown <= 0;}
    @Override public int getDamage() {return super.getDamage() + (weapon != null ? weapon.getDamage() : 5);}
    public float getVelocityY() {return velocityY;}
    public void setVelocityY(float velocityY) {this.velocityY = velocityY;}
    public void setMapViewer(MapViewer mapViewer){this.mapViewer = mapViewer;}
    public boolean isOnGround() {return onGround;}
    public void setOnGround(boolean onGround) {this.onGround = onGround;}
    public static float getJumpSpeed() {return JUMP_SPEED;}
    public void setVisual(LayeredPlayer visualMock) {visual = visualMock;}
    public void setAttackCooldown(float attackCooldown) {this.attackCooldown = attackCooldown;}

    public void dispose() {
        if (visual != null) {
            visual.dispose();
        }
    }


}
