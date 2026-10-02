package fr.javaquest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class Game extends ApplicationAdapter {
    private static final float VIEW_WIDTH = 640f;
    private static final float VIEW_HEIGHT = 360f;
    private static final float PLAYER_SIZE = 32f;
    private static final float PLAYER_SPEED = 200f;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;

    private float mapWidth, mapHeight;
    private float playerX = 100, playerY = 100; // à placer sur une case accessible

    @Override
    public void create() {
        map = new TmxMapLoader().load("FirstMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        mapWidth = map.getProperties().get("width", Integer.class)
                * map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class)
                * map.getProperties().get("tileheight", Integer.class);

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(VIEW_WIDTH, VIEW_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        renderer.setView(camera);
        renderer.render();

        // Joueur provisoire (carré rouge)
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerX, playerY, PLAYER_SIZE /2, PLAYER_SIZE);
        shapeRenderer.end();
    }

    private void update(float delta) {

        // Déplacement du joueur
        float step = PLAYER_SPEED * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  playerX -= step;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerX += step;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    playerY += step;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  playerY -= step;

        // Quitter le jeu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        // Le joueur reste dans la map
        playerX = MathUtils.clamp(playerX, 0, mapWidth - PLAYER_SIZE);
        playerY = MathUtils.clamp(playerY, 0, mapHeight - PLAYER_SIZE);

        // La caméra suit le joueur, sans sortir de la map
        float halfW = camera.viewportWidth / 2f;
        float halfH = camera.viewportHeight / 2f;
        float targetX = playerX + PLAYER_SIZE / 2f;
        float targetY = playerY + PLAYER_SIZE / 2f;

        camera.position.x = (mapWidth <= camera.viewportWidth)
                ? mapWidth / 2f
                : MathUtils.clamp(targetX, halfW, mapWidth - halfW);
        camera.position.y = (mapHeight <= camera.viewportHeight)
                ? mapHeight / 2f
                : MathUtils.clamp(targetY, halfH, mapHeight - halfH);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        shapeRenderer.dispose();
    }
}