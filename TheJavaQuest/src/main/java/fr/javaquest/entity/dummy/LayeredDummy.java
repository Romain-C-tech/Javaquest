package fr.javaquest.entity.dummy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.javaquest.entity.npc.NpcRenderer;

public class LayeredDummy extends Dummy {

    private NpcRenderer character;

    public LayeredDummy(DummyData data, float x, float y) {
        super(data, x, y);
        character = new NpcRenderer(data.renderFile);
    }

    @Override
    public void render(SpriteBatch batch) {
        character.x = hitbox.x;
        character.y = hitbox.y;
        character.draw(batch);
    }

    @Override
    public void dispose() {
        character.dispose();
    }
}
