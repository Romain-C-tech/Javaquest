package fr.javaquest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity {
    private static final float JUMP_SPEED = 400f;
    private static final float GRAVITY = 1000f;
    private static final float GROUND_Y = 347f; // provisoire, à remplacer par les collisions Tiled

    private float velocityY = 0;
    private boolean onGround = true;
    private final Texture idleTexture;

    public Player(float x, float y) {

        super(100, 200f, x, y);

        idleTexture = new Texture("Player/Idle/Idle-Sheet.png");

        TextureRegion[][] tmp = TextureRegion.split(
            idleTexture,
            idleTexture.getWidth()/4,
            idleTexture.getHeight()
        );

        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4 ; i++){
            idleAnimation = new
        }


        hitbox.setSize(50,80);
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

    public void render(SpriteBatch batch){
        batch.draw(
            idleTexture,
            hitbox.x,
            hitbox.y,
            hitbox.width,
            hitbox.height
        );
    };
    public void dispose() {};
}
