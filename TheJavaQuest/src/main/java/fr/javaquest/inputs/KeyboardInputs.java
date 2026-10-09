package fr.javaquest.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import fr.javaquest.entity.Player;
import fr.javaquest.entity.enemies.Enemy;

import java.util.List;

public class KeyboardInputs {
    private final Player player;
    private final List<Enemy> enemies;
    public boolean isMovingRight;
    public boolean isMovingLeft;

    public KeyboardInputs(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void update(float delta) {
        // Sur un clavier AZERTY, la touche Q correspond à Keys.A (LibGDX utilise la position physique)
        boolean moving = false;
        if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            player.moveLeft(delta);
            isMovingLeft = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            player.moveRight(delta);
            isMovingRight = true;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) System.out.println("Pressed S");
        if (Gdx.input.isKeyPressed(Keys.DOWN)) System.out.println("Pressed Down arrow");
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.UP)) {
            player.jump();
            moving = true;
        }
        if (!moving) {
            player.idle();
        }
        if (Gdx.input.isKeyJustPressed(Keys.F)) {
            player.attack(enemies);
        }

        isMovingLeft = false;
        isMovingRight = false;
    }
}
