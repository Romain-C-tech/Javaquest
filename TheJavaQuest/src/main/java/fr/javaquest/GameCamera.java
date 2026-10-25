package fr.javaquest;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Mise en place de la vision du joueur par une camera qui va suivre le personnage.
 *
 * @author ssamepi
 */
public class GameCamera {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ExtendViewport viewport;
    private float mapWidth;
    private float mapHeight;

    /**
     * Mise en place de la caméra.
     * @param viewWidth , prise en compte de la longueur de l'écran du joueur.
     * @param viewHeight , prise en compte de la hauteur de l'écran du joueur.
     * @param mapWidth , prise en compte de la longueur de la carte.
     * @param mapHeight , prise en compte de la longueur de la carte.
     *
     * @author Romain-C-tech
     */
    public GameCamera(float viewWidth, float viewHeight, float mapWidth, float mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.viewport = new ExtendViewport(viewWidth, viewHeight, camera);
    }

    /**
     * Récupération de la position du personnage du joueur pour fixé la caméra à partir du personnage.
     * @param targetX est la position x du personnage.
     * @param targetY est la position y du personnage.
     *
     * @author Romain-C-tech
     */
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

    /**
     * Permet de changer la taille de la caméra pour s'adapter à une nouvelle carte.
     * @param width , est la largeur de la caméra.
     * @param height , est la hauteur de la caméra.
     *
     * @author Romain-C-tech
     */
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Prise en compte de la taille de la carte.
     * @param mapWidth est la longueur totale de la carte.
     * @param mapHeight est la hauteur totale de la carte.
     *
     * @author Romain-C-tech
     */
    public void setMapSize(float mapWidth, float mapHeight){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }
}
