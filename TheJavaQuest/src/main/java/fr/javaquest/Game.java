package fr.javaquest;

import com.badlogic.gdx.Screen;
import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;
import fr.javaquest.menu.SceneUI;

/**
 * Gestion de l'affichage du menu et du jeu.
 *
 * @author ssamepi, Romain-C-tech
 */
public class Game extends com.badlogic.gdx.Game {
    private GameplayScreen gameplayScreen;
    private SceneUI menuScreen;

    private static final float[] BRIGHTNESS_VALUES = {1f, 0.7f, 0.4f};
    private static final String[] BRIGHTNESS_LABELS = {"Normal", "Mix", "DarkMode"};
    private int brightnessLevel = 0;

    public void cycleBrightness() {
        brightnessLevel = (brightnessLevel + 1) % BRIGHTNESS_VALUES.length;
    }

    public float getBrightness() {
        return BRIGHTNESS_VALUES[brightnessLevel];
    }

    public String getBrightnessLabel() {
        return BRIGHTNESS_LABELS[brightnessLevel];
    }

    /**
     * Chargement des armes/armures/ennemis/mannequin/menu pour le premier affichage.
     *
     * @author ssamepi, Romain-C-tech
     */
    @Override
    public void create() {
        WeaponRegistry.load();
        ArmorRegistry.load();
        EnemyRegistry.load();
        DummyRegistery.load();
        setScreen(getMenuScreen());
    }
    public GameplayScreen getGameScreen(){
        return gameplayScreen;
    }
    public void setGameScreen(GameplayScreen gameplayScreen){
        this.gameplayScreen = gameplayScreen;
    }

    /**
     * Création d'un menu ou réutilisation d'un menu existant.
     *
     * @return pour avoir le menu.
     * @author ssamepi
     */
    public Screen getMenuScreen() {
        if (menuScreen == null){
            menuScreen = new SceneUI(this);
        }
        return menuScreen;
    }
}
