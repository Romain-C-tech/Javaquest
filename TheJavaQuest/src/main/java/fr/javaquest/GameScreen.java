package fr.javaquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.javaquest.entity.Player;
import fr.javaquest.entity.enemies.Enemy;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.equipments.Rarity;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorRegistry;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;
import fr.javaquest.inputs.KeyboardInputs;

public class GameScreen extends ScreenAdapter {
    private final MapViewer mapViewer = new MapViewer("Map/ForestMap/ForestMap.tmx");
    private final GameCamera gameCamera =
            new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
    private final Player player = new Player(100, 300, mapViewer);
    private final Player player = new Player(100, 100);
    private final Enemy dummy = new Enemy(EnemyRegistry.get("training_dummy"), 500, 347);
    private final KeyboardInputs inputs = new KeyboardInputs(player);
    private final SpriteBatch batch = new SpriteBatch();

    public GameScreen() {
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        player.equip(new Armor(ArmorRegistry.get("iron_helmet"), Rarity.COMMON));
        player.equip(new Armor(ArmorRegistry.get("iron_chestplate"), Rarity.LEGEND));
        player.equip(new Armor(ArmorRegistry.get("iron_leggings"), Rarity.NORMAL));
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        inputs.update(delta);
        player.update(delta);
        dummy.update(delta);

        player.keepInside(mapViewer.getWidth(), mapViewer.getHeight());

        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(hitbox.x + hitbox.width / 2f, hitbox.y + hitbox.height / 2f);

        ScreenUtils.clear(0, 0, 0, 1);
        gameCamera.apply();
        mapViewer.render(gameCamera.getCamera());

        batch.setProjectionMatrix(gameCamera.getCamera().combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameCamera.resize(width, height);
    }

    @Override
    public void dispose() {
        mapViewer.dispose();
        batch.dispose();
        player.dispose();
    }
}
