package fr.javaquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.javaquest.entity.Player;
import fr.javaquest.inputs.KeyboardInputs;

public class GameScreen extends ScreenAdapter {
    private final MapViewer mapViewer = new MapViewer("Map/ForestMap/ForestMap.tmx");
    private final GameCamera gameCamera =
            new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
    private final Player player = new Player(100, 300, mapViewer);
    private final KeyboardInputs inputs = new KeyboardInputs(player);
    private final SpriteBatch batch = new SpriteBatch();

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        inputs.update(delta);
        player.update(delta);
        player.keepInside(mapViewer.getWidth(), mapViewer.getHeight());

        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(hitbox.x + hitbox.width / 2f, hitbox.y + hitbox.height / 2f);

        ScreenUtils.clear(0, 0, 0, 1);
        gameCamera.apply();
        mapViewer.render(gameCamera.getCamera());

        batch.setProjectionMatrix(gameCamera.getCamera().combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameCamera.resize(width, height);
    }

    @Override
    public void dispose() {
        mapViewer.dispose();
        batch.dispose();
        player.dispose();
    }
}
