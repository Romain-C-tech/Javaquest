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
import fr.javaquest.entity.enemies.LayeredEnemy;
import fr.javaquest.entity.enemies.TrainingDummy;
import fr.javaquest.equipments.Rarity;
import fr.javaquest.equipments.armors.Armor;
import fr.javaquest.equipments.armors.ArmorRegistry;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;
import fr.javaquest.hud.Hud;
import fr.javaquest.inputs.KeyboardInputs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    private final MapViewer mapViewer;
    private final GameCamera gameCamera;
    private final Player player;
    private final SpriteBatch batch;
    private final List<Enemy> enemies = new ArrayList<>();
    private final KeyboardInputs inputs;
    private final Hud hud;

    public GameScreen() {
        mapViewer = new MapViewer("Map/ForestMap/ForestMap.tmx");
        gameCamera = new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
        player = new Player(100, 300, mapViewer);
        inputs = new KeyboardInputs(player, enemies);
        batch = new SpriteBatch();
        hud = new Hud(batch, player);

        equipPlayer();
        spawnEnemies();
    }

    public void equipPlayer() {
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        player.equip(new Armor(ArmorRegistry.get("iron_helmet"), Rarity.COMMON));
        player.equip(new Armor(ArmorRegistry.get("iron_chestplate"), Rarity.LEGEND));
        player.equip(new Armor(ArmorRegistry.get("iron_leggings"), Rarity.NORMAL));
    }

    public void spawnEnemies() {
        spawnNpc("training_dummy", 400, 192);
    }

    private void spawnNpc(String id, float x, float y) {
        enemies.add(new TrainingDummy(EnemyRegistry.get(id), x, y));
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
