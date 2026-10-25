package fr.javaquest.entity.equipments.armors;

import fr.javaquest.entity.equipments.Equipment;
import fr.javaquest.entity.equipments.Rarity;

/**
 * Défini ce qu'est une armure pour pouvoir l'équiper.
 *
 * @author Romain-C-tech
 */
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
