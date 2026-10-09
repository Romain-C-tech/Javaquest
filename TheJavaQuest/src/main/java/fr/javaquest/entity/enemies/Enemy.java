package fr.javaquest.entity.enemies;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import fr.javaquest.entity.Entity;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;

public abstract class Enemy extends Entity {

    protected static final float JUMP_SPEED = 400f;
    protected float velocityY = 0f;
    protected boolean onGround = true;
    protected final EnemyData data;
    private final EnemySteerable steerable;
    private SteeringBehavior<Vector2> steeringBehavior;
    private final SteeringAcceleration<Vector2> steeringAcceleration;

    public Enemy(EnemyData data, float x, float y) {
        super(data.hp, data.damage, data.movementSpeed, data.attackSpeed,
                createWeapon(data), x, y);
        this.data = data;
        hitbox.setSize(data.hitbox.width, data.hitbox.height);

        this.steerable = new EnemySteerable(this);
        this.steeringAcceleration = new SteeringAcceleration<>(new Vector2());
    }

    private static Weapon createWeapon(EnemyData data) {
        if (data.weapon == null) return null;
        return new Weapon(WeaponRegistry.get(data.weapon), data.weaponRarity);
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> behavior) {this.steeringBehavior = behavior;}
    public EnemySteerable getSteerable() {return steerable;}

    public void moveLeft(float delta)  { hitbox.x -= movementSpeed * delta; isFacingRight = false; }
    public void moveRight(float delta) { hitbox.x += movementSpeed * delta; isFacingRight = true; }

    public void jump() {
        if (onGround) {
            this.velocityY = JUMP_SPEED;
            this.onGround = false;
        }
    }

    @Override public void update(float delta) {
        if (steeringBehavior != null) {
            steerable.updatePositionFromHitbox();

            steeringBehavior.calculateSteering(steeringAcceleration);

            steerable.applySteering(steeringAcceleration,delta);
        }
    }

    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}