package fr.javaquest.entity.enemies.ai;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.javaquest.entity.enemies.Enemy;

public class EnemySteerable implements Steerable<Vector2> {

    private final Enemy enemy;
    private final Vector2 position;
    private final Vector2 linearVelocity;
    private float orientation;
    private float angularVelocity;

    private float maxLinearAcceleration = 500f;
    private float maxAngularSpeed = 0f;
    private final float maxAngularAcceleration = 0f;
    private boolean tagged;
    private float zeroLinearSpeedThreshold = 0.001f;

    public EnemySteerable(Enemy enemy) {
        this.enemy = enemy;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        updatePositionFromHitbox();
    }

    /**
     * Synchronise la position du Steerable avec le centre de la Hitbox de l'ennemi.
     */
    public void updatePositionFromHitbox() {
        Rectangle hb = enemy.getHitbox();
        this.position.set(hb.x + hb.width / 2f, hb.y + hb.height / 2f);
    }

    /**
     * Intègre l'accélération horizontale du steering dans la vélocité de l'ennemi.
     */
    public void applySteering(SteeringAcceleration<Vector2> steering, float delta) {
        linearVelocity.x += steering.linear.x * delta;

        float maxSpeed = enemy.getMovementSpeed();
        linearVelocity.x = Math.max(-maxSpeed, Math.min(linearVelocity.x, maxSpeed));

        if (linearVelocity.x < -zeroLinearSpeedThreshold) {
            enemy.setFacingRight(false);
        } else if (linearVelocity.x > zeroLinearSpeedThreshold) {
            enemy.setFacingRight(true);
        }
    }

    @Override
    public float getMaxLinearSpeed() {
        return enemy.getMovementSpeed();
    }

    @Override
    public float getBoundingRadius() {
        return enemy.getHitbox().width / 2f;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override public float getOrientation() { return orientation; }
    @Override public void setOrientation(float orientation) { this.orientation = orientation; }
    @Override public float getAngularVelocity() { return angularVelocity; }
    @Override public boolean isTagged() { return tagged; }
    @Override public void setTagged(boolean tagged) { this.tagged = tagged; }
    @Override public void setMaxLinearSpeed(float maxLinearSpeed) { }
    @Override public float getMaxLinearAcceleration() { return maxLinearAcceleration; }
    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration) { this.maxLinearAcceleration = maxLinearAcceleration; }
    @Override public float getMaxAngularSpeed() { return maxAngularSpeed; }
    @Override public void setMaxAngularSpeed(float maxAngularSpeed) { }
    @Override public float getMaxAngularAcceleration() { return maxAngularAcceleration; }
    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration) { }
    @Override public float getZeroLinearSpeedThreshold() { return zeroLinearSpeedThreshold; }
    @Override public void setZeroLinearSpeedThreshold(float value) { this.zeroLinearSpeedThreshold = value; }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return new EnemyLocation();
    }

    private static class EnemyLocation implements Location<Vector2> {
        private final Vector2 position = new Vector2();
        private float orientation;

        @Override public Vector2 getPosition() { return position; }
        @Override public float getOrientation() { return orientation; }
        @Override public void setOrientation(float orientation) { this.orientation = orientation; }
        @Override public float vectorToAngle(Vector2 vector) { return (float) Math.atan2(-vector.x, vector.y); }
        @Override public Vector2 angleToVector(Vector2 outVector, float angle) {
            outVector.x = -(float) Math.sin(angle);
            outVector.y = (float) Math.cos(angle);
            return outVector;
        }
        @Override public Location<Vector2> newLocation() { return new EnemyLocation(); }
    }
}