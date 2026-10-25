package fr.javaquest;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


 /**

 * Point d'entrée du jeu "JavaQuest".
 * <p>
 * La classe Main configure l'environnement et lance le jeu.
 * </p>
 *
 * @author ssamepi, Romain-C-tech
 */


public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("JavaQuest");
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.useVsync(true);
        new Lwjgl3Application(new Game(), config);
    }
}
