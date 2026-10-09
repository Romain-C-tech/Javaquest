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
import com.badlogic.gdx.Input.Keys;

public class GameScreen extends ScreenAdapter {
    private MapViewer mapViewer = new MapViewer("Map/VillageMap/FirstMap.tmx");
    private GameCamera gameCamera = new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
    private final Player player = new Player(100, 350, mapViewer);
    private final Enemy dummy = new Enemy(EnemyRegistry.get("training_dummy"), 500, 347);
    private final KeyboardInputs inputs = new KeyboardInputs(player);
    private final SpriteBatch batch = new SpriteBatch();

    private void changeMap (String mapPath, float spawnX, float spawnY){
        MapViewer oldMap = mapViewer;
        mapViewer = new MapViewer(mapPath);
        gameCamera.setMapSize(mapViewer.getWidth(), mapViewer.getHeight());
        player.setMap(mapViewer);
        player.setPosition(spawnX, spawnY);
        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(
            hitbox.x + hitbox.width / 2f,
            hitbox.y + hitbox.height / 2f
        );
        oldMap.dispose();
    }
    private void checkTeleportToForest(){
        for (Rectangle rectangle : mapViewer.getForestTeleportRectangles()){
            if (rectangle.overlaps(player.getHitbox()) && Gdx.input.isKeyPressed(Keys.E)){
                changeMap(
                    "Map/ForestMap/ForestMap.tmx",
                    100,
                    190);
                return;
            }
        }
    }

    private  void checkTeleportToVillage(){
        for (Rectangle rectangle : mapViewer.getTeleportToVillageRectangles()){
            if (rectangle.overlaps(player.getHitbox())&& Gdx.input.isKeyPressed(Keys.E)){
                changeMap(
                    "Map/VillageMap/FirstMap.tmx",
                    100,
                    350);
                return;
            }
        }
    }

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

        checkTeleportToForest();
        checkTeleportToVillage();

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
