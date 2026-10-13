package fr.javaquest.equipments.armors;

import fr.javaquest.equipments.Equipment;
import fr.javaquest.equipments.Rarity;

public class Armor implements Equipment {
    private final ArmorData data;
    private final Rarity rarity;

    public Armor(ArmorData data, Rarity rarity) {
        this.data = data;
        this.rarity = rarity;
    }

    public int getHp()   { return Math.round(data.hp * rarity.multiplier); }
    public ArmorSlot getSlot() { return data.slot; }

    public String getName()    { return data.name; }
    public Rarity getRarity() {return rarity;}
}
