package fr.javaquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.javaquest.entity.equipments.Rarity;
import fr.javaquest.entity.playerEntity.Player;
import fr.javaquest.entity.playerEntity.PlayerSteerable;
import fr.javaquest.entity.enemies.Enemy;
import fr.javaquest.entity.enemies.EnemyRegistry;
import fr.javaquest.entity.enemies.LayeredEnemy;
import fr.javaquest.entity.dummy.Dummy;
import fr.javaquest.entity.dummy.DummyRegistery;
import fr.javaquest.entity.dummy.LayeredDummy;
import fr.javaquest.entity.equipments.armors.Armor;
import fr.javaquest.entity.equipments.armors.ArmorRegistry;
import fr.javaquest.entity.equipments.weapons.Weapon;
import fr.javaquest.entity.equipments.weapons.WeaponRegistry;
import fr.javaquest.hud.Hud;
import fr.javaquest.inputs.KeyboardInputs;
import com.badlogic.gdx.Input.Keys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Rendu visuel affiché sur l'écran du joueur.
 * <p>
 *      Permet l'affichage des différentes cartes de jeux, du joueur, l'affichage d'ennemis et le changement entre carte de jeux.
 * </p>
 *
 * @author ssamepi, Romain-C-tech
 */
public class GameplayScreen extends ScreenAdapter {
    private MapViewer mapViewer;
    private GameCamera gameCamera;
    private final Player player;
    private final PlayerSteerable playerSteerable;
    private final SpriteBatch batch;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Dummy> dummies = new ArrayList<>();
    private final KeyboardInputs inputs;
    private final Hud hud;
    private final Game javaQuest;

    /**
     * Affichage du rendu du jeu avec la carte de départ, le joueur et un mannequin d'entraînement.
     * <p>
     *     On retrouve le suivi de la camera centrée sur le joueur et l'emplacement initial du joueur.
     *     Il y a également l'affichage des déplacements du joueur.
     * </p>
     *
     * @param javaQuest instance de jeu qui est lancé
     * @author ssamepi, Romain-C-tech
     */
    public GameplayScreen(Game javaQuest) {
        this.javaQuest = javaQuest;
        mapViewer = new MapViewer("Map/VillageMap/FirstMap.tmx");
        gameCamera = new GameCamera(640f, 360f, mapViewer.getWidth(), mapViewer.getHeight());
        player = new Player(100, 355, mapViewer);
        inputs = new KeyboardInputs(player, enemies);
        playerSteerable = new PlayerSteerable(player, inputs);
        batch = new SpriteBatch();
        hud = new Hud(batch, player);

        equipPlayer();
        spawnDummy();
    }

    /**
     * Fonction qui permet de changer de carte de jeu.
     * <p>
     *     Destruction de toutes les anciennes entités de l'ancienne carte de jeu.
     *     Création d'un nouveau visuel pour la carte.
     *     La caméra prend maintenant la taille de la nouvelle carte en compte.
     *     La position initiale du joueur est donnée en fonction de la carte.
     *     L'ancienne carte est détruite.
     * </p>
     *
     * @param mapPath chemin qui permet l'affichage de la carte.
     * @param spawnX position x où le joueur va apparaitre.
     * @param spawnY position y où le joueur va apparaitre.
     *
     * @author ssamepi
     */
    private void changeMap(String mapPath, float spawnX, float spawnY) {
        MapViewer oldMap = mapViewer;
        for (Enemy enemy : enemies) enemy.dispose();
        enemies.clear();
        for (Dummy dummy : dummies) dummy.dispose();
        dummies.clear();
        mapViewer = new MapViewer(mapPath);
        gameCamera.setMapSize(mapViewer.getWidth(), mapViewer.getHeight());
        player.setMapViewer(mapViewer);
        player.setPosition(spawnX, spawnY);
        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(
                hitbox.x + hitbox.width / 2f,
                hitbox.y + hitbox.height / 2f
        );
        oldMap.dispose();
    }

    /**
     * Vérification qui permet au joueur de changer pour la carte "Forêt".
     * <p>
     *     On vérifie que le joueur est bien dans la zone et qu'il appuie sur E.
     *     Si c'est le cas on change de carte et appelle la fonction qui crée le/les ennemie(s) associés à la carte.
     * </p>
     *
     * @author ssamepi
     */
    private void checkTeleportToForest() {
        for (Rectangle rectangle : mapViewer.getForestTeleportRectangles()) {
            if (rectangle.overlaps(player.getHitbox()) && Gdx.input.isKeyPressed(Keys.E)) {
                changeMap(
                        "Map/ForestMap/ForestMap.tmx",
                        100,
                        190);
                spawnForestEnemies();
                return;
            }
        }
    }

    /**
     * Vérification qui permet au joueur de changer pour la carte "Village".
     * <p>
     *     On vérifie que le joueur est bien dans la zone et qu'il appuie sur E.
     *     Si c'est le cas on change de carte et appelle la fonction qui crée le/les ennemie(s) associés à la carte.
     * </p>
     *
     * @author ssamepi
     */
    private void checkTeleportToVillage() {
        for (Rectangle rectangle : mapViewer.getTeleportToVillageRectangles()) {
            if (rectangle.overlaps(player.getHitbox()) && Gdx.input.isKeyPressed(Keys.E)) {
                changeMap(
                        "Map/VillageMap/FirstMap.tmx",
                        100,
                        350);
                spawnDummy();
                return;
            }
        }
    }

    /**
     * Vérification qui permet au joueur de récupérer des points de vie perdu.
     * <p>
     *     Vérification si le joueur est dans une zone donnée.
     * </p>
     */
    private void checkRestoreLife(){
        for (Rectangle rectangle : mapViewer.getRestoreLifeRectangle()){
            if (rectangle.overlaps(player.getHitbox())){
                player.restoreLife(1);
            }
            return;
        }
    }


    public void equipPlayer() {
        player.equip(new Weapon(WeaponRegistry.get("iron_sword"), Rarity.NORMAL));
        player.equip(new Armor(ArmorRegistry.get("iron_helmet"), Rarity.COMMON));
        player.equip(new Armor(ArmorRegistry.get("iron_chestplate"), Rarity.LEGEND));
        player.equip(new Armor(ArmorRegistry.get("iron_leggings"), Rarity.NORMAL));
    }

    /**
     * Permet d'affiché un mannequin d'entrainement aux positions données.
     *
     * @ author Romain-C-tech
     */
    public void spawnDummy() {
        dummies.add(new LayeredDummy(DummyRegistery.get("training_dummy"), 400, 347));
    }

    /**
     * Création des ennemies avec leur id et la position où chaque ennemi va apparaitre.
     *
     * @author Romain-C-tech
     */
    public void spawnForestEnemies() {
        spawnEnemy("wild_boar", 320, 288);
        spawnEnemy("wild_boar", 320,192);
        spawnEnemy("wild_boar", 100,415);
        spawnEnemy("wild_white_boar", 640,478);
        spawnEnemy("wild_white_boar", 580,478);
        spawnEnemy("wild_white_boar", 988,640);
        spawnEnemy("wild_black_boar", 1200,318);
        spawnEnemy("wild_black_boar", 1250,318);
        spawnEnemy("wild_black_boar", 1800,349);
        spawnEnemy("wild_black_boar", 1550,542);
        spawnEnemy("wild_black_boar", 1600,542);
        spawnEnemy("wild_black_boar", 1650,542);
        spawnEnemy("wild_black_boar", 1700,542);
        spawnEnemy("red_eyes_grizzly_boss", 2750,192);
    }

    /**
     *
     * Permet la création d'un ennemi.
     *
     * @param id nom de l'ennemi
     * @param x position de l'ennemi en x
     * @param y position de l'ennemi en y
     *
     * @author Romain-C-tech
     */
    private void spawnEnemy(String id, float x, float y) {
        enemies.add(new LayeredEnemy(EnemyRegistry.get(id), x, y, mapViewer, playerSteerable));
    }

    /**
     * Affichage dynamique (joueur/ennemis) et mise en pause du jeu possible.
     * <p>
     *     Gestion d'un timing pour retirer l'entité apriès avoir fait son animation de mort.
     * </p>
     * @param delta Le temps en secondes depuis le dernier rendu.
     * {@link Hud}
     *
     * @author ssamepi, Romain-C-tech
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            javaQuest.setScreen(javaQuest.getMenuScreen());
            javaQuest.pause();
            return;
        }

        inputs.update(delta);
        player.update(delta);
        playerSteerable.update();
        hud.update();
        for (Enemy enemy : enemies) enemy.update(delta);

        checkTeleportToForest();
        checkTeleportToVillage();
        checkRestoreLife();

        player.keepInside(mapViewer.getWidth(), mapViewer.getHeight());

        Rectangle hitbox = player.getHitbox();
        gameCamera.follow(hitbox.x + hitbox.width / 2f, hitbox.y + hitbox.height / 2f);

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            if (enemy.isRemovable()) {
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
