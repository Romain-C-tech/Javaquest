package fr.javaquest.entity.PlayerEntity;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.javaquest.inputs.KeyboardInputs;

public class PlayerSteerable implements Steerable<Vector2> {
    private final Player player;
    private final Vector2 position = new Vector2();
    private final Vector2 linearVelocity = new Vector2();
    private final KeyboardInputs inputs;

    public PlayerSteerable(Player player, KeyboardInputs inputs) {
        this.player = player;
        this.inputs = inputs;
    }

    public void update() {
        Rectangle hitbox = player.getHitbox();
        this.position.set(hitbox.x + hitbox.width / 2f,hitbox.y + hitbox.height / 2f);

        float vy = player.getVelocityY();
        float vx = 0f;
        if (inputs.isMovingRight) {
            vx = player.getMovementSpeed();
        }
        if (inputs.isMovingLeft) {
            vx = -player.getMovementSpeed();
        }
        this.linearVelocity.set(vx,vy);
    }


    @Override public Vector2 getPosition() {return position;}
    @Override public Vector2 getLinearVelocity() {return linearVelocity;}

    public Player getPlayer() {return player;}

    @Override public float getAngularVelocity() {return 0;}
    @Override public float getBoundingRadius() {return 0;}
    @Override public boolean isTagged() {return false;}
    @Override public void setTagged(boolean tagged) {}
    @Override public float getZeroLinearSpeedThreshold() {return 0;}
    @Override public void setZeroLinearSpeedThreshold(float value) {}
    @Override public float getMaxLinearSpeed() {return 0;}
    @Override public void setMaxLinearSpeed(float maxLinearSpeed) {}
    @Override public float getMaxLinearAcceleration() {return 0;}
    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration) {}
    @Override public float getMaxAngularSpeed() {return 0;}
    @Override public void setMaxAngularSpeed(float maxAngularSpeed) {}
    @Override public float getMaxAngularAcceleration() {return 0;}
    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration) {}

    @Override public float getOrientation() {return 0;}
    @Override public void setOrientation(float orientation) {}
    @Override public float vectorToAngle(Vector2 vector) {return 0;}
    @Override public Vector2 angleToVector(Vector2 outVector, float angle) {return null;}
    @Override public Location<Vector2> newLocation() {return null;}
}
