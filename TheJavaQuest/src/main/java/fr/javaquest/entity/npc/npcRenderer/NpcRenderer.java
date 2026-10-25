package fr.javaquest.entity.npc.npcRenderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Affichage des entités non hostiles.
 *
 * @author ssamepi
 */
public class NpcRenderer {
    private final Texture texture;
    private final TextureRegion frame;

    public float x;
    public float y;

    /**
     * Récupération du png
     * @param file est le chemin où est le png.
     *
     * @author Romain-C-tech
     */
    public NpcRenderer(String file) {
        texture = new Texture(file);

        texture.setFilter(
                Texture.TextureFilter.Nearest,
                Texture.TextureFilter.Nearest
        );

        frame = new TextureRegion(texture);
    }

    /**
     * Affichage de l'image.
     * @param batch est un lot de sprite.
     *
     * @author Romain-C-tech
     */
    public void draw(SpriteBatch batch) {
        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        batch.draw(frame, x, y , width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
