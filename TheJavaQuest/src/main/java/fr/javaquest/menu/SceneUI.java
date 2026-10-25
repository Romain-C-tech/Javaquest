package fr.javaquest.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import fr.javaquest.Game;
import fr.javaquest.GameplayScreen;


/**
 * Affichage du menu et de ses boutons.
 *
 * @author ssamepi
 */
public class SceneUI extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game javaQuest;
    private BrightnessOverlay overlay;

    public SceneUI(Game javaQuest){
        this.javaQuest = javaQuest;
    }

    /**
     * Mise en place du menu et de ses boutons.
     * <p>
     *     Gestion de la taille, couleur, espacement des boutons.
     *     Gestion des actions pour chaque bouton.
     * </p>
     *
     * @author ssamepi
     */
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("Asset/Ui/uiskin.json"));
        overlay = new BrightnessOverlay();

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.defaults().center().width(300).height(60).space(20);
        stage.addActor(mainTable);

        TextButton playButton = new TextButton("Play / Restart", skin);
        mainTable.add(playButton);

        TextButton resumeButton = new TextButton("Resume", skin);
        mainTable.add(resumeButton).row();
        resumeButton.setDisabled(javaQuest.getGameScreen() == null);

        TextButton brightnessButton = new TextButton("Luminosity: " + javaQuest.getBrightnessLabel(), skin);
        mainTable.add(brightnessButton);

        TextButton exitButton = new TextButton("Exit", skin);
        mainTable.add(exitButton);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameplayScreen oldGame = javaQuest.getGameScreen();
                if (oldGame != null){
                    oldGame.dispose();
                }
                GameplayScreen newGame = new GameplayScreen(javaQuest);
                javaQuest.setGameScreen(newGame);
                javaQuest.setScreen(newGame);
            }
        });
        resumeButton.addListener(new ChangeListener() {
                 @Override
                 public void changed(ChangeEvent event, Actor actor) {
                     javaQuest.setScreen(javaQuest.getGameScreen());
                     javaQuest.resume();
                 }
             });
        brightnessButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                javaQuest.cycleBrightness();
                brightnessButton.setText("Luminosity: "+ javaQuest.getBrightnessLabel());
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    /**
     * Permet de redéfinir la taille de la caméra.
     * @param width est la longueur.
     * @param height est la hauteur.
     *
     * @author ssamepi
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    /**
     * Permet l'affichage dynamique.
     * @param delta Le temps en secondes depuis le dernier affichage.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
        overlay.render(javaQuest.getBrightness());
    }

    /**
     * Permet de cacher le menu.
     * <p>
     *     Cette fonction permet de garder le menu associé au jeu et d'alterné entre le menu et le jeu.
     * </p>
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        overlay.dispose();
    }

}
