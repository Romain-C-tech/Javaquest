package fr.javaquest;

import com.badlogic.gdx.Screen;
import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;
import fr.javaquest.menu.SceneUI;

public class Game extends com.badlogic.gdx.Game {
    private GameScreen gameScreen;
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
    @Override
    public void create() {
        WeaponRegistry.load();
        ArmorRegistry.load();
        EnemyRegistry.load();
        DummyRegistery.load();
        setScreen(getMenuScreen());
    }
    public GameScreen getGameScreen(){
        return gameScreen;
    }
    public void setGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public Screen getMenuScreen() {
        if (menuScreen == null){
            menuScreen = new SceneUI(this);
        }
        return menuScreen;
    }
}
