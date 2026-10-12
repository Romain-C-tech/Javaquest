package fr.javaquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.javaquest.entity.Player;
import fr.javaquest.entity.dummy.Dummy;
import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.dummy.LayeredDummy;
import fr.javaquest.entity.enemies.*;
import fr.javaquest.equipments.Rarity;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorRegistry;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;
import fr.javaquest.hud.Hud;
import fr.javaquest.inputs.KeyboardInputs;
import com.badlogic.gdx.Input.Keys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private MapViewer mapViewer;
    private GameCamera gameCamera;
    private final Player player;
    private final SpriteBatch batch;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Dummy> dummies = new ArrayList<>();
    private final KeyboardInputs inputs;
    private final Hud hud;

    public GameScreen() {
        mapViewer = new MapViewer("Map/VillageMap/FirstMap.tmx");
        gameCamera = new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
        player = new Player(100, 350, mapViewer);
        inputs = new KeyboardInputs(player, enemies);
        batch = new SpriteBatch();
        hud = new Hud(batch, player);

        equipPlayer();
        spawnDummy();
    }

    private void changeMap (String mapPath, float spawnX, float spawnY){
        MapViewer oldMap = mapViewer;
        for (Enemy enemy : enemies) enemy.dispose();
        for (Dummy dummy : dummies) dummy.dispose();
        enemies.clear();
        dummies.clear();
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
                spawnDummy();
                return;
            }
        }
    }



    public void equipPlayer() {
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        player.equip(new Armor(ArmorRegistry.get("iron_helmet"), Rarity.COMMON));
        player.equip(new Armor(ArmorRegistry.get("iron_chestplate"), Rarity.LEGEND));
        player.equip(new Armor(ArmorRegistry.get("iron_leggings"), Rarity.NORMAL));
    }

    public void spawnDummy() {
        dummies.add(new LayeredDummy(DummyRegistery.get("training_dummy"), 400, 347));
    }

    public void spawnEnemies() {

    }
    private void spawnNpc(String id, float x, float y) {
    }
    private void spawnEnemy(String id, float x, float y) {
        enemies.add(new LayeredEnemy(EnemyRegistry.get(id),x,y));
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        inputs.update(delta);
        player.update(delta);
        for (Enemy enemy : enemies) enemy.update(delta);

        checkTeleportToForest();
        checkTeleportToVillage();

        player.keepInside(mapViewer.getWidth(), mapViewer.getHeight());

        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(hitbox.x + hitbox.width / 2f, hitbox.y + hitbox.height / 2f);

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (enemy.isDead()) {
                enemy.dispose();
                it.remove();
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);
        gameCamera.apply();
        mapViewer.render(gameCamera.getCamera());

        batch.setProjectionMatrix(gameCamera.getCamera().combined);
        batch.begin();
        for (Enemy enemy : enemies) {enemy.render(batch);}
        for (Dummy dummy : dummies) {dummy.render(batch);}
        player.render(batch);
        batch.end();

        hud.stage.act(delta);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameCamera.resize(width, height);
        hud.resize(width,height);
    }

    @Override
    public void dispose() {
        mapViewer.dispose();
        hud.dispose();
        batch.dispose();
        player.dispose();
    }
}
