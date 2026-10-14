package fr.javaquest;

import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;

public class Game extends com.badlogic.gdx.Game {
    @Override
    public void create() {
        WeaponRegistry.load();
        ArmorRegistry.load();
        EnemyRegistry.load();
        DummyRegistery.load();
        setScreen(new GameScreen());
    }
}
