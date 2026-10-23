package fr.javaquest;

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

public class SceneUI extends ScreenAdapter {

    private Stage stage;
    private Skin skin;
    private final Game javaQuest;

    public SceneUI(Game javaQuest){
        this.javaQuest = javaQuest;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("Asset/Ui/uiskin.json"));

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

        TextButton parametersButton = new TextButton("Parameters", skin);
        mainTable.add(parametersButton);
        parametersButton.setDisabled(javaQuest.getGameScreen() == null); // desactive pour le moment vu qu'on a pas la fonctionnalité

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

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}
