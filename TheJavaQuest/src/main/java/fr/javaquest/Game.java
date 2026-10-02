package fr.javaquest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Game extends ApplicationAdapter {
    private static final float VIEW_WIDTH = 640f;
    private static final float VIEW_HEIGHT = 360f;
    private static final float PLAYER_SIZE = 32f;
    private static final float PLAYER_SPEED = 200f;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameCamera gameCamera;
    private ShapeRenderer shapeRenderer;

    private float mapWidth, mapHeight;
    private float playerX = 100, playerY = 100;

    @Override
    public void create() {
        map = new TmxMapLoader().load("FirstMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        mapWidth = map.getProperties().get("width", Integer.class)
                * map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class)
                * map.getProperties().get("tileheight", Integer.class);

        gameCamera = new GameCamera(VIEW_WIDTH, VIEW_HEIGHT, mapWidth, mapHeight);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCamera.apply();
        renderer.setView(gameCamera.getCamera());
        renderer.render();

        shapeRenderer.setProjectionMatrix(gameCamera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        shapeRenderer.end();
    }

    private void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        float step = PLAYER_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  playerX -= step;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += step;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    playerY += step;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  playerY -= step;

        playerX = MathUtils.clamp(playerX, 0, mapWidth - PLAYER_SIZE);
        playerY = MathUtils.clamp(playerY, 0, mapHeight - PLAYER_SIZE);

        gameCamera.follow(playerX + PLAYER_SIZE / 2f, playerY + PLAYER_SIZE / 2f);
    }

    @Override
    public void resize(int width, int height) {
        gameCamera.resize(width, height);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        shapeRenderer.dispose();
    }
}