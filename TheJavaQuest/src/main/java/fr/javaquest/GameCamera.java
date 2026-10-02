package fr.javaquest;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameCamera {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ExtendViewport viewport;
    private final float mapWidth;
    private final float mapHeight;

    public GameCamera(float viewWidth, float viewHeight, float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.viewport = new ExtendViewport(viewWidth, viewHeight, camera);
    }

    public void follow(float targetX, float targetY) {
        float halfW = camera.viewportWidth / 2f;
        float halfH = camera.viewportHeight / 2f;

        camera.position.x = (mapWidth <= camera.viewportWidth)
                ? mapWidth / 2f
                : MathUtils.clamp(targetX, halfW, mapWidth - halfW);
        camera.position.y = (mapHeight <= camera.viewportHeight)
                ? mapHeight / 2f
                : MathUtils.clamp(targetY, halfH, mapHeight - halfH);
        camera.update();
    }

    public void apply() {
        viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}