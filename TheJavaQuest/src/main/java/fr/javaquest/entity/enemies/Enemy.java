package fr.javaquest.entity.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import fr.javaquest.entity.Entity;
import fr.javaquest.equipments.weapons.Weapon;
import fr.javaquest.equipments.weapons.WeaponRegistry;

public class Enemy extends Entity {

    private final EnemyData data;
    private final Color color;

    public Enemy(EnemyData data, float x, float y) {
        super(data.hp, data.damage, data.movementSpeed, data.attackSpeed,
                createWeapon(data), x, y);
        this.data = data;
        this.color = Color.valueOf(data.visual.color);
        hitbox.setSize(data.hitbox.width, data.hitbox.height);
    }

    private static Weapon createWeapon(EnemyData data) {
        if (data.weapon == null) return null;
        return new Weapon(WeaponRegistry.get(data.weapon), data.weaponRarity);
    }

    public void moveLeft(float delta)  { hitbox.x -= movementSpeed * delta; isFacingRight = false;}
    public void moveRight(float delta) { hitbox.x += movementSpeed * delta; isFacingRight = true;}

    @Override public void update(float delta) {}

    @Override
    public void render(ShapeRenderer shapes) {
        float p = data.visual.padding;
        shapes.setColor(color);
        shapes.rect(hitbox.x + p, hitbox.y + p,
                hitbox.width - 2 * p, hitbox.height - 2 * p);
    }
}
