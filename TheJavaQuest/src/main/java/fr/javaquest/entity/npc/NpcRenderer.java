package fr.javaquest.entity.npc.npcRenderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NpcRenderer {
    private final Texture texture;
    private final TextureRegion frame;

    public float x;
    public float y;

    public NpcRenderer(String file) {
        texture = new Texture(file);

        texture.setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
        );

        frame = new TextureRegion(texture);
    }

    public void draw(SpriteBatch batch) {
        draw(batch, 0);
    }

    public void draw(
            SpriteBatch batch,
            float offSetY
    ) {
        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        batch.draw(frame, x, y + offSetY, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
