package fr.javaquest.entity.equipments.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.List;
import java.util.Objects;

/**
 * Animation d'attaque et Affichage pour chaque arme.
 * <p>
 *     L'arme de base est la SwordIron.
 *     Le joueur peux changer d'arme.
 *
 * </p>
 */
public class WeaponRenderer {

    private Animation<TextureRegion> CurrentWeapon;
    private Animation<TextureRegion> Stick;
    private Animation<TextureRegion> AxeWood;
    private Animation<TextureRegion> AxeCopper;
    private Animation<TextureRegion> AxeIron;
    private Animation<TextureRegion> ArrowWood;
    private Animation<TextureRegion> ArrowCopper;
    private Animation<TextureRegion> ArrowIron;
    private Animation<TextureRegion> SwordWood;
    private Animation<TextureRegion> SwordCopper;
    private Animation<TextureRegion> SwordIron;

    private final Array<Texture> textures = new Array<>();



    private boolean attacking = false;
    private float attackTime = 0f;

    public float x;
    public float y;

    public WeaponRenderer(){
        Stick = load(
            "Asset/PixelCrawlerFreePack/Weapons/Stick/StickAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        AxeWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeWoodAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        AxeCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeCopperAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        AxeIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeIronAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        ArrowIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Bow/ArrowIronAnimation.png",
            0.15f,
            0,
            3,
            32,
            32
        );
        ArrowCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Bow/ArrowCopperAnimation.png",
            0.15f,
            0,
            3,
            32,
            32
        );
        ArrowWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Bow/ArrowWoodAnimation.png",
            0.15f,
            0,
            3,
            32,
            32
        );
        SwordCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordCopperAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        SwordIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordIronAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );
        SwordWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordWoodAnimation.png",
            0.15f,
            0,
            2,
            32,
            32
        );

        if (getCurrentWeapon() == null){
            CurrentWeapon = SwordIron;   // Doit être changer si on fait le changement d'arme
        }

    }
    public Animation<TextureRegion> getCurrentWeapon() {
        return CurrentWeapon;
    }

    public void setCurrentWeapon(Animation<TextureRegion> currentWeapon) {this.CurrentWeapon = currentWeapon; }

    /**
     * Chargement du png, découpage en plusieurs images et joue l'animation.
     *
     * @param file est le chemin où le png est stocké.
     * @param frameTime est la durée d'animation en seconde pour chaque image.
     * @param from est l'indice de la première image.
     * @param to est l'indice de la dernière image.
     * @param frameWidth est la largeur de chaque image découpée.
     * @param frameHeight est la hauteur de chaque image découpée.
     * @return joue l'animation
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

            Animation<TextureRegion> animation = new Animation<>(frameTime, subset);
            animation.setPlayMode(Animation.PlayMode.NORMAL);
            return animation;
    }

    public void startAttack(){
        if (attacking) return;
        attacking = true;
        attackTime = 0f;
    }

    /**
     * Joue l'animation d'attaque.
     *
     * @param delta Le temps en secondes depuis le dernier rendu.
     *
     * @author ssamepi
     */
    public void updateAnimation(float delta) {
        if (!attacking) return;

        attackTime += delta;

        if (CurrentWeapon.isAnimationFinished(attackTime)) {
            attacking = false;
            attackTime = 0f;
        }
    }

    public float getAttackTime(){
        return attacking ? attackTime : 0f;
    }

    public void setWeapon() {
        Animation<TextureRegion> weapon = getCurrentWeapon();
        List<String> allWeapons = List.of("Stick", "AxeWood", "AxeCopper",
            "AxeIron", "ArrowWood", "ArrowCopper",
            "ArrowIron", "SwordWood", "SwordCopper", "SwordIron");

        for (String allWeapon : allWeapons) {
            if (Objects.equals(weapon.toString(), allWeapon)) {
                CurrentWeapon = weapon;
            }
        }
    }

    public void dispose() {
        for (Texture t : textures) t.dispose();
    }

}
