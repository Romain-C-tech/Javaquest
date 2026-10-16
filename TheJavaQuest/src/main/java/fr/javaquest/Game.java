package fr.javaquest;

import com.badlogic.gdx.Screen;
import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;

public class Game extends com.badlogic.gdx.Game {
    private GameScreen gameScreen;
    private SceneUI menuScreen;

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
