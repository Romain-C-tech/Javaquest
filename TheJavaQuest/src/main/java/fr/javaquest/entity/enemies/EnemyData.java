package fr.javaquest.entity.enemies;

import fr.javaquest.entity.HitboxSize;
import fr.javaquest.entity.equipments.Rarity;

public class EnemyData {
    public String id;
    public String name;
    public int hp;
    public int damage;
    public float movementSpeed;
    public float attackSpeed;
    public HitboxSize hitbox;
    public String renderFile;
    public String weapon;
    public Rarity weaponRarity = Rarity.COMMON;

    public EnemyData() {}
}
