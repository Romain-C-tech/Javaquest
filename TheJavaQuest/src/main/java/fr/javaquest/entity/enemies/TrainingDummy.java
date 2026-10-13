package fr.javaquest.entity.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.MapViewer;
import fr.javaquest.entity.PlayerEntity.PlayerSteerable;
import fr.javaquest.entity.npcRenderer.NpcRenderer;

public class TrainingDummy extends Enemy {

    private final NpcRenderer renderer;

    public TrainingDummy(EnemyData data, float x, float y, MapViewer mapViewer, PlayerSteerable targetSteerable) {
        super(data, x, y, mapViewer, targetSteerable);
        renderer = new NpcRenderer(data.renderFile);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderer.x = hitbox.x;
        renderer.y = hitbox.y;
        renderer.draw(batch);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
