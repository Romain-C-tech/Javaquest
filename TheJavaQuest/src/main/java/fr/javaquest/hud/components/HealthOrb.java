package fr.javaquest.hud.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

/**
 * Affichage de la sphère de vie.
 */
public class HealthOrb extends Actor implements Disposable {
    private static final int LIQ_TOP = 4, LIQ_H = 54;

    private final Texture texCadre, texLiquide;
    private final TextureRegion cadre, liquide;
    private final float max;
    private float valeur, affichee;

    public HealthOrb(float max, float echelle) {
        this.max = max;
        valeur = affichee = max;

        texCadre   = new Texture("Asset/PixelCrawlerFreePack/HealthOrb/orb_cadre.png");
        texLiquide = new Texture("Asset/PixelCrawlerFreePack/HealthOrb/orb_liquide.png");

        texCadre.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        texLiquide.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        cadre   = new TextureRegion(texCadre);
        liquide = new TextureRegion(texLiquide);

        setSize(texCadre.getWidth() * echelle, texCadre.getHeight() * echelle);
    }

    /**
     * Méthode permettant de modifier la valeur de la vie sur l'HUD.
     * @param v
     * @author Romain-C-tech
     */
    public void setValeur(float v) { valeur = MathUtils.clamp(v, 0, max); }

    @Override
    public void act(float delta) {
        super.act(delta);
        affichee += (valeur - affichee) * Math.min(1f, delta * 6f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1, 1, 1, parentAlpha);
        float s = getWidth() / texCadre.getWidth();

        int h = Math.round(LIQ_H * affichee / max);
        if (h > 0) {
            int yBas = LIQ_TOP + LIQ_H;
            liquide.setRegion(0, yBas - h, texLiquide.getWidth(), h);
            float y = getY() + (texCadre.getHeight() - yBas) * s;
            batch.draw(liquide, getX(), y, getWidth(), h * s);
        }

        batch.draw(cadre, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() { texCadre.dispose(); texLiquide.dispose(); }

    /**
     * Méthode permettant de syncroniser la taille de l'HUD en fonction de l'écran.
     * @param e
     * @author Romain-C-tech
     */
    public void setEchelle(int e) {
        setSize(texCadre.getWidth() * e, texCadre.getHeight() * e);
    }
}
