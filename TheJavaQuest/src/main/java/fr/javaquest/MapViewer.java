package fr.javaquest;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class MapViewer {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final float width;
    private final float height;

    // on stock les collisions dans une liste
    private final Array<Rectangle> collisionsRectangles = new Array<>();
    private final Array<Rectangle> teleportToForestRectangles = new Array<>();
    private final Array<Rectangle> teleportToVillageRectangles = new Array<>();


    public MapViewer(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);

        width = map.getProperties().get("width", Integer.class)
                * map.getProperties().get("tilewidth", Integer.class);
        height = map.getProperties().get("height", Integer.class)
                * map.getProperties().get("tileheight", Integer.class);

        loadCollisions();
    }

    private void loadCollisions(){
        MapLayer collisionLayer = map.getLayers().get("collision");
        MapLayer teleportToForestCollisionLayer = map.getLayers().get("ToForest");
        MapLayer teleportToVillageCollisionLayer = map.getLayers().get("ToVillage");

        if (collisionLayer == null){
            System.out.println("aucune zone de collisions trouvé pour cette carte.");
            return;
        }
        else {
            for (MapObject object : collisionLayer.getObjects()){

                if (object instanceof RectangleMapObject rectangleObject) {
                    Rectangle rectangle = rectangleObject.getRectangle();
                    collisionsRectangles.add(new Rectangle(rectangle));
                }
            }
        }
        if (teleportToForestCollisionLayer == null){
            System.out.println("pas de zone de tp pour aller dans la forêt.");
        }
        else {
            for (MapObject object : teleportToForestCollisionLayer.getObjects()){

                if (object instanceof RectangleMapObject rectangleObject) {
                    Rectangle rectangleOfForestTp = rectangleObject.getRectangle();
                    teleportToForestRectangles.add(new Rectangle(rectangleOfForestTp));
                }
            }
        }
        if (teleportToVillageCollisionLayer == null){
            System.out.println("pas de zone de tp pour retourner au village");
        }
        else {
            for (MapObject object : teleportToVillageCollisionLayer.getObjects()){
                if (object instanceof RectangleMapObject rectangleObject){
                    Rectangle rectangleOfVillageTp = rectangleObject.getRectangle();
                    teleportToVillageRectangles.add(new Rectangle(rectangleOfVillageTp));
                }
            System.out.println(teleportToVillageRectangles.size);
            }
        }


    }

    public boolean collides(Rectangle hitbox){
        for (Rectangle collision : collisionsRectangles){
            if (hitbox.overlaps(collision)){
                return true;
            }
        }
        return false;
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public Array<Rectangle> getCollisionsRectangles(){
        return collisionsRectangles;
    }
    public float getWidth()  { return width; }
    public float getHeight() { return height; }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
    public Array<Rectangle> getForestTeleportRectangles(){
        return teleportToForestRectangles;
    }
    public Array<Rectangle> getTeleportToVillageRectangles(){
        return teleportToVillageRectangles;
    }
}
