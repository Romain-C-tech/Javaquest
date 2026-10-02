package fr.javaquest;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapViewer {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final float width;
    private final float height;

    public MapViewer(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);

        width = map.getProperties().get("width", Integer.class)
                * map.getProperties().get("tilewidth", Integer.class);
        height = map.getProperties().get("height", Integer.class)
                * map.getProperties().get("tileheight", Integer.class);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public float getWidth()  { return width; }
    public float getHeight() { return height; }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}