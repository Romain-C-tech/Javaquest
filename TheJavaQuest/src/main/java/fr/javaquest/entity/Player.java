package fr.javaquest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.entity.enemies.Enemy;
import fr.javaquest.entity.enemies.TrainingDummy;
import com.badlogic.gdx.utils.Array;
import fr.javaquest.MapViewer;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorSlot;
import fr.javaquest.equipments.weapons.Weapon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.entity.layerPlayer.*;

import java.util.List;

public class Player extends Entity {

    private static final float JUMP_SPEED = 400f;
    private static final float GRAVITY = 1000f;

    private final LayeredCharacter visual;
    private float attackCooldown = 0f;
    private float velocityY = 0;
    private boolean onGround = true;

    private final Array<Texture> textures = new Array<>();
    private final MapViewer mapViewer;

    public Player(float x, float y, MapViewer mapViewer) {
        super(100,5 ,200f,1F,null, x, y);
        visual = new LayeredCharacter();
        this.mapViewer = mapViewer;
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

        visual.facingRight = false;
        visual.setRun();
    }

    public void moveRight(float delta) {
        float nextX = hitbox.x + movementSpeed * delta;
        if (canMoveTo(nextX, hitbox.y)){
            hitbox.x = nextX;
        }
        visual.facingRight = true;
        visual.setRun();
    }
    public void idle(){ visual.setIdle(); }

    public void jump() {
        if (onGround) {
            onGround = false;
            velocityY = JUMP_SPEED;
        }
    }

    public void equip(Weapon newWeapon) {
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

    public void attack(List<Enemy> enemies) {
        if (!canAttack()) return;

        attackCooldown = 1f / weapon.getAttackSpeed();

        Rectangle zone = weapon.getAttackHitbox(hitbox, visual.facingRight);

        for (Enemy enemy : enemies) {
            if (zone.overlaps(enemy.getHitbox())) {
                if (!(enemy instanceof TrainingDummy)) {
                    enemy.takeDamage(getDamage());
                }
                return;
            }
        }
    }

    private boolean canAttack() {
        return weapon != null && attackCooldown <= 0;
    }


    @Override
    public int getDamage() {
        return super.getDamage() + (weapon != null ? weapon.getDamage() : 0);
    }

    @Override
    public int getMaxHp() {
        int total = 200;
        for (Armor armor : armors.values()) {
            total += armor.getHp();
        }
        return total;
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

        visual.update(delta);

        if (attackCooldown > 0f) attackCooldown -= delta;
    }

    public void render(SpriteBatch batch) {
        Rectangle hb = getHitbox();

        visual.x =
                hb.x + hb.width / 2f - 32 / 2f;

        visual.y = hb.y;

        visual.draw(batch);
    }

    public void dispose() {
        visual.dispose();
    }
}
