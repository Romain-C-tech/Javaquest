package fr.javaquest.entity.weaponRenderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class WeaponRenderer {

    private Animation<TextureRegion> CurrentWeapon;
    private Animation<TextureRegion> Stick;
    private Animation<TextureRegion> AxeWood;
    private Animation<TextureRegion> AxeCopper;
    private Animation<TextureRegion> AxeIron;
    private Animation<TextureRegion> ArrowWood;
    private Animation<TextureRegion> ArrowCopper;
    private Animation<TextureRegion> ArrowIron;
    private Animation<TextureRegion> ShieldWood;
    private Animation<TextureRegion> ShieldCopper;
    private Animation<TextureRegion> ShieldIron;
    private Animation<TextureRegion> SwordWood;
    private Animation<TextureRegion> SwordCopper;
    private Animation<TextureRegion> SwordIron;

    private final Array<Texture> textures = new Array<>();

    private float stateTime = 0f;

    public float x;
    public float y;

    public boolean facingRight = true;

    public WeaponRenderer(){
        Stick = load(
            "Asset/PixelCrawlerFreePack/Weapons/Stick/StickAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );
        AxeWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeWoodAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );
        AxeCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeCopperAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );
        AxeIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Axe/AxeIronAnimation.png",
            0.15f,
            0,
            0,
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
        ShieldCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldCopperAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );
        ShieldIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldIronAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );
        ShieldWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Shield/ShieldWoodAnimation.png",
            0.15f,
            0,
            0,
            16,
            16
        );
        SwordCopper = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordCopperAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );
        SwordIron = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordIronAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );
        SwordWood = load(
            "Asset/PixelCrawlerFreePack/Weapons/Sword/SwordWoodAnimation.png",
            0.15f,
            0,
            0,
            32,
            32
        );

        CurrentWeapon = Stick;

    }
    public Animation<TextureRegion> getCurrentWeapon() {
        return CurrentWeapon;
    }

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

}
