package fr.javaquest.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.javaquest.entity.Player;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private HealthOrb orbeVie;

    private Player player;

    private final Array<Texture> textures = new Array<>();

    public Hud(SpriteBatch batch, Player player) {
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        this.player = player;

        orbeVie = new HealthOrb(player.getMaxHp(),1);

        stage.addActor(orbeVie);
    }

    public void setVie(float vie) {
        orbeVie.setValeur(vie);
    }


    public void resize(int w, int h) {
        if (w == 0 || h == 0) return;
        viewport.update(w, h, true);

        int echelle = Math.max(1, h / 270);
        orbeVie.setEchelle(echelle);
        orbeVie.setPosition(6 * echelle, 6 * echelle);
    }

    @Override
    public void dispose() {
        orbeVie.dispose();
    }
}