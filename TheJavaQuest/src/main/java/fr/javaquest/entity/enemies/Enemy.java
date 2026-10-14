package fr.javaquest.entity.enemies;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.Entity;
import fr.javaquest.entity.HitboxSize;
import fr.javaquest.entity.PlayerEntity.PlayerSteerable;
import fr.javaquest.entity.enemies.ai.EnemyState;
import fr.javaquest.entity.enemies.ai.EnemySteerable;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;

public abstract class Enemy extends Entity {

    private final DefaultStateMachine<Enemy, EnemyState> stateMachine;
    private PlayerSteerable targetSteerable;

    protected float velocityY = 0f;
    protected boolean onGround = true;
    protected final EnemyData data;
    protected final MapViewer mapViewer;
    protected final Rectangle visual;
    private final EnemySteerable steerable;
    private SteeringBehavior<Vector2> steeringBehavior;
    private final SteeringAcceleration<Vector2> steeringAcceleration;

    private SteeringBehavior<Vector2> pursueBehavior;

    private float attackCooldown = 0f;

    public Enemy(EnemyData data, float x, float y, MapViewer mapViewer, PlayerSteerable targetSteerable) {
        super(data.hp, data.damage, data.movementSpeed, data.attackSpeed,
                createWeapon(data), x, y);
        this.data = data;
        hitbox.setSize(data.hitbox.width, data.hitbox.height);
        this.targetSteerable = targetSteerable;

        this.steerable = new EnemySteerable(this);
        this.steeringAcceleration = new SteeringAcceleration<>(new Vector2());
        this.visual = new Rectangle(x,y, 20,20); // TODO : replace by visual size.
        this.mapViewer = mapViewer;

        initPursueSteeringBehavior(this.targetSteerable);
        this.stateMachine = new DefaultStateMachine<>(this, EnemyState.PATROL);
    }

    private static Weapon createWeapon(EnemyData data) {
        if (data.weapon == null) return null;
        return new Weapon(WeaponRegistry.get(data.weapon), data.weaponRarity);
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> behavior) {this.steeringBehavior = behavior;}
    public EnemySteerable getSteerable() {return steerable;}
    public SteeringBehavior<Vector2> getPursueBehavior() {return this.pursueBehavior;}

    public PlayerSteerable getTargetSteerable() {return targetSteerable;}
    public DefaultStateMachine<Enemy, EnemyState> getStateMachine() {return stateMachine;}

    public void setFacingRight(boolean facingRight) { isFacingRight = facingRight; }

    public void initPursueSteeringBehavior(PlayerSteerable playerSteerable) {
        this.targetSteerable = playerSteerable;
        this.pursueBehavior = new Pursue<>(this.steerable, this.targetSteerable);
    }

    public void attack() {
        if (!canAttack()) return;
        attackCooldown = 1f / data.attackSpeed;

        HitboxSize hitboxData = data.hitbox;
        float x = isFacingRight ? this.hitbox.x + hitboxData.width : this.hitbox.x - hitbox.width;
        float y = this.hitbox.y;

        Rectangle zone = new Rectangle(x,y, hitboxData.width, hitboxData.height);

        Vector2 targetpos = targetSteerable.getPosition();

        float playerWidth = 32f;
        float playerHeight = 32f;
        Rectangle targetHitbox = new Rectangle(
                targetpos.x - playerHeight /2,
                targetpos.y - playerWidth /2,
                playerWidth,
                playerHeight);

        if (zone.overlaps(targetHitbox)) {
            targetSteerable.getPlayer().takeDamage(data.damage); //TODO: Add Armor reduction.
        }
    }

    public boolean canAttack() {
        return attackCooldown <= 0 && attackSpeed > 0f;
    }

    @Override public void update(float delta) {
        steerable.updatePositionFromHitbox();

        if (movementSpeed > 0f) {
            stateMachine.update();
        }

        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(steeringAcceleration);

            steerable.applySteering(steeringAcceleration,delta);

            if (onGround) {
                velocityY = 0;
            }

            float newX = hitbox.x + steerable.getLinearVelocity().x * delta;
            float newY = hitbox.y + velocityY * delta;

            hitbox.x = newX;

            Rectangle futureHitbox = new Rectangle(
                    hitbox.x,
                    newY,
                    hitbox.width,
                    hitbox.height
            );

            boolean collision = false;

            for (Rectangle collider : mapViewer.getCollisionsRectangles()) {

                if (futureHitbox.overlaps(collider)) {

                    collision = isFalling(collider, collision);
                    if (collision) break;

                    collision = isJumping(collider, collision);
                }
            }

            if (!collision) {
                hitbox.y = newY;
                onGround = false;
            }

        }
        if (attackCooldown > 0f) attackCooldown -= delta;
    }

    private boolean isJumping(Rectangle collider, boolean collision) {
        if (velocityY > 0 && hitbox.y + hitbox.height <= collider.y + 1) {

            hitbox.y = collider.y - hitbox.height;
            velocityY = 0;
            collision = true;
        }
        return collision;
    }

    private boolean isFalling(Rectangle collider, boolean collision) {
        if (velocityY <= 0 && hitbox.y >= collider.y + collider.height - 1) {

            hitbox.y = collider.y + collider.height;
            velocityY = 0;
            onGround = true;
            collision = true;
        }
        return collision;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}