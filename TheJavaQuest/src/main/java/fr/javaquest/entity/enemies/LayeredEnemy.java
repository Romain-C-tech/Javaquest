package fr.javaquest.entity.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.entity.layerPlayer.LayeredCharacter;

public class LayeredEnemy extends Enemy {

    private final LayeredCharacter character;

    public LayeredEnemy(EnemyData data, float x, float y) {
        super(data, x, y);
        character = new LayeredCharacter();
    }

    @Override
    public void update(float delta) {
        character.update(delta);
    }


    @Override
    public void render(SpriteBatch batch) {
        character.x = hitbox.x;
        character.y = hitbox.y;
        character.facingRight = isFacingRight;
        character.draw(batch);
    }

    @Override
    public void dispose() {
        character.dispose();
    }
}
