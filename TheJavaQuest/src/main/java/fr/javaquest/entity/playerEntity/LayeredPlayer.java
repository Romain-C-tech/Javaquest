package fr.javaquest.entity.playerEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import fr.javaquest.entity.equipments.weapons.WeaponRenderer;

/**
 * Animations du personnage.
 * <p>
 *     Statique, course, mort, avec un bouclier, avec une épée, les mains.
 *     L'animation de base est "statique".
 * </p>
 *
 * @author ssamepi
 */
public class LayeredPlayer {

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> death;
    private Animation<TextureRegion> hands;

    private Animation<TextureRegion> currentAnimation;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;

    public boolean facingRight = true;
    private WeaponRenderer weapon;
    private ShieldRenderer shield;

    public LayeredPlayer() {

        idle = load(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Idle/Idle-Sheet.png",
            0.15f,
            0,
            3,
            32,
            32
        );

        run = loadRun(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Run/Run-Sheet.png",
            0.15f
        );

        death = load(
            "Asset/PixelCrawlerFreePack/Entities/Npcs/Knight/Death/Death-Sheet.png",
            0.15f,
            0,
            8,
            32,
            32
        );

        hands = load(
            "Asset/PixelCrawlerFreePack/Weapons/Hands/Hands.png",
            0.15f,
            0,
            0,
            32,
            16
        );
        shield = new ShieldRenderer();
        weapon = new WeaponRenderer();
        currentAnimation = idle;
    }

    /**
     * Découpage d'un png.
     *
     * @param file est le chemin où est le png.
     * @param frameTime est la durée allouée à chaque image découpée.
     * @param from est l'indice de la première image.
     * @param to est l'indice de la dernière image.
     * @param frameWidth est la taille en largueur de chaque image découpée.
     * @param frameHeight est la taille en hauteur de chaque image découpée.
     * @return renvois la durée allouée à chaque image découpée et une liste contenant toutes les images.
     */
    private Animation<TextureRegion> load(
        String file,
        float frameTime,
        int from,
        int to,
        int frameWidth,
        int frameHeight
    ) {
        Texture texture = new Texture(file);

        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        textures.add(texture);

        TextureRegion[][] regions =
            TextureRegion.split(
                texture,
                frameWidth,
                frameHeight
            );

        TextureRegion[] all =
            new TextureRegion[
                regions.length * regions[0].length
                ];

        int index = 0;

        for (TextureRegion[] row : regions) {
            for (TextureRegion region : row) {
                all[index++] = region;
            }
        }

        TextureRegion[] subset =
            java.util.Arrays.copyOfRange(
                all,
                from,
                to + 1
            );

        return new Animation<>(
            frameTime,
            subset
        );
    }
    /**
     * Découpage d'un png.
     * <p>
     *     Récupération de la partie nécessaire du png.
     *     Le png ayant un format spécial vis a vis des autres animations il faut récupérer la partie voulu sur chaque image
     *     avant de les stockés dans la liste.
     * </p>
     * @param file est le chemin où est le png.
     * @param frameTime est la durée allouée à chaque image découpée.
     * @return renvois la durée allouée à chaque image découpée et une liste contenant toutes les images.
     */
    private Animation<TextureRegion> loadRun(
        String file,
        float frameTime
    ) {
        Texture texture = new Texture(file);

        texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        textures.add(texture);

        TextureRegion[] frames = new TextureRegion[6];

        for (int i = 0; i < 6; i++) {

            int x = i * 64;

            frames[i] = new TextureRegion(
                texture,
                x + 17,
                32,
                32,
                30
            );
        }

        return new Animation<>(
            frameTime,
            frames
        );
    }

    public void update(float delta) {
        stateTime += delta;
        weapon.updateAnimation(delta);
    }

    public void setIdle() {
        if (currentAnimation != idle) {
            currentAnimation = idle;
            stateTime = 0f;
        }
    }

    public void setRun() {
        if (currentAnimation != run) {
            currentAnimation = run;
            stateTime = 0f;
        }
    }

    public void setDeath() {
        if (currentAnimation != death) {
            currentAnimation = death;
            stateTime = 0f;
        }
    }

    public void startAttack(){
        weapon.startAttack();
    }

    /**
     * Affichage complet du joueur avec son équipement.
     *
     * @param batch est un lot de sprite.
     *
     * @author ssamepi
     */
    public void draw(SpriteBatch batch) {
        drawLayer(batch, currentAnimation, 0, 0, 0);
        drawWeaponLayer(batch, weapon.getCurrentWeapon(), weapon.getAttackTime(), 18,18, 0);
        drawLayer(batch, hands, 1, -1, 0);
        if (shield.getCurrentShield() != null){drawLayer(batch, shield.getCurrentShield(), 0, -18, 0);}
    }

    /**
     * Affichage complet de l'image en fonction de la direction de déplacement du personnage.
     *
     * @param batch est un lot de sprite.
     * @param animation est l'animation à jouée.
     * @param offSetRightX est la position de joueur sur l'axe des x.
     * @param offSetLeftX est la position du joueur sur l'axe des y.
     * @param offSetY sert à décalé l'image sur l'axe des y.
     *
     * @author ssamepi
     */
    private void drawLayer(
        SpriteBatch batch,
        Animation<TextureRegion> animation,
        float offSetRightX,
        float offSetLeftX,
        float offSetY
    ) {
        TextureRegion frame =
            animation.getKeyFrame(
                stateTime,
                true
            );

        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        if (facingRight) {

            batch.draw(
                frame,
                x + offSetRightX,
                y + offSetY,
                width,
                height
            );

        } else {
            batch.draw(
                frame,
                x - offSetLeftX + width,
                y + offSetY,
                -width,
                height
            );
        }
    }

    /**
     * Affichage complet de l'arme en fonction de la direction de déplacement.
     *
     * @param batch est un lot de sprite.
     * @param animation est l'animation à jouée.
     * @param offSetRightX est la position de joueur sur l'axe des x.
     * @param offSetLeftX est la position du joueur sur l'axe des y.
     * @param offSetY sert à décalé l'image sur l'axe des y.
     *
     * @author ssamepi
     */
    private void drawWeaponLayer(
        SpriteBatch batch,
        Animation<TextureRegion> animation,
        float time,
        float offSetRightX,
        float offSetLeftX,
        float offSetY
    ) {
        TextureRegion frame =
            animation.getKeyFrame(
                time,
                false
            );

        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        if (facingRight) {

            batch.draw(
                frame,
                x + offSetRightX,
                y + offSetY,
                width,
                height
            );

        } else {
            batch.draw(
                frame,
                x - offSetLeftX + width,
                y + offSetY,
                -width,
                height
            );
        }
    }

    public boolean isFacingRight() {return facingRight;}
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
