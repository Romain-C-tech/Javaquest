package fr.javaquest.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class BrightnessOverlay implements Disposable {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public void render(float brightness) {
        float alpha = 1f - brightness;
        if (alpha <= 0f) return;

        Gdx.gl.glEnable(GL20.GL_BLEND); // pour la partie qui s'assombit
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // met le rectangle sombre devant

        shapeRenderer.getProjectionMatrix().setToOrtho2D(
            0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        ); // départ en bas a gauche et on prend la taille de l'écran
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // ouvre le "cadre" pour mettre le filtre"
        shapeRenderer.setColor(0, 0, 0, alpha);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end(); // on ferme le cadre pour l'afficher

        Gdx.gl.glDisable(GL20.GL_BLEND); // arrete la transparence pour que ce ne sois fais que sur ce rectangle
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
