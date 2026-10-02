package fr.javaquest.entity;

public class Player extends Entity {
    private static final float JUMP_SPEED = 400f;
    private static final float GRAVITY = 1000f;
    private static final float GROUND_Y = 347f; // provisoire, à remplacer par les collisions Tiled

    private float velocityY = 0;
    private boolean onGround = true;

    public Player(float x, float y) {
        super(100, 200f, x, y);
    }

    public void moveLeft(float delta)  { hitbox.x -= movementSpeed * delta; }
    public void moveRight(float delta) { hitbox.x += movementSpeed * delta; }

    public void jump() {
        if (onGround) {
            onGround = false;
            velocityY = JUMP_SPEED;
        }
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