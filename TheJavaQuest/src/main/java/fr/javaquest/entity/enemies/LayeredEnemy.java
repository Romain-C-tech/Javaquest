package fr.javaquest.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.playerEntity.PlayerSteerable;
import fr.javaquest.entity.playerEntity.LayeredPlayer;

public class LayeredEnemy extends Enemy {

    private Animation<TextureRegion> run;
    private Animation<TextureRegion> death;

    private final LayeredPlayer character;

    public LayeredEnemy(EnemyData data, float x, float y, MapViewer mapViewer, PlayerSteerable targetSteerable) {
        super(data, x, y, mapViewer, targetSteerable);
        character = new LayeredPlayer();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
