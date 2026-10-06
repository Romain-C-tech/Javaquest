package fr.javaquest.equipments.weapons;

import fr.javaquest.equipments.Rarity;

public class Weapon {
    private final WeaponData data;
    private final Rarity rarity;

    public Weapon(WeaponData data, Rarity rarity) {
        this.data = data;
        this.rarity = rarity;
    }

    public int getDamage() {return Math.round(data.damage * rarity.multiplier);}
    public float getAttackSpeed() {return data.attackSpeed;}

    public Rarity getRarity() {return rarity;}
    public String getName() {return data.name;}
}
