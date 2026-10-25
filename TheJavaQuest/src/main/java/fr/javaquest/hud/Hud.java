package fr.javaquest.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.javaquest.entity.playerEntity.Player;
import fr.javaquest.hud.components.HealthOrb;
import fr.javaquest.hud.components.PlayerPos;

/**
 * Affichage HUD pour la vie du joueur.
 *
 * <p>
 *     L'affichage est fixe et la barre de vie (en rouge) évolue en fonction des points de vie restant du joueur.
 *     La barre de vie se base sur les points de vie du joueur plus des points de vie données par l'équipement qu'il possède.
 * </p>
 *
 * @author Romain-C-tech
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private HealthOrb orbeVie;
    private PlayerPos playerPos;
    private Player player;


    public Hud(SpriteBatch batch, Player player) {
        viewport = new ScreenViewport();
        stage = new Stage(viewport, batch);
        this.player = player;

        orbeVie = new HealthOrb(510,1); // player.getMaxHp() a la place de "510" quand la vie que donne l'armure sera ajoutée au joueur
        playerPos = new PlayerPos(player);

        stage.addActor(orbeVie);
        stage.addActor(playerPos);
    }

    public void setVie(float vie) {
        orbeVie.setValeur(vie);
    }


    /**
     * Affichage qui change en fonction de la taille d'écran du joueur.
     *
     * @param w est la longueur d'écran du joueur.
     * @param h est la hauteur d'écran du joueur.
     *
     * @author ssamepi
     */
    public void resize(int w, int h) {
        if (w == 0 || h == 0) return;
        viewport.update(w, h, true);

        int echelle = Math.max(1, h / 270);
        orbeVie.setEchelle(echelle);
        orbeVie.setPosition(6 * echelle, 6 * echelle);
    }

    public void update() {
        setVie(player.getHp());
    }

    @Override
    public void dispose() {
        orbeVie.dispose();
    }
}
