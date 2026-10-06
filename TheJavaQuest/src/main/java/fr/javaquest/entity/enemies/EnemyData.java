package fr.javaquest.entity.enemies;

import fr.javaquest.equipments.Rarity;

public class EnemyData {
    public String id;
    public String name;
    public int hp;
    public int damage;
    public float movementSpeed;
    public float attackSpeed;
    public HitboxSize hitbox;
    public VisualData visual;
    public String weapon;
    public Rarity weaponRarity = Rarity.COMMON;

    public EnemyData() {}
}
