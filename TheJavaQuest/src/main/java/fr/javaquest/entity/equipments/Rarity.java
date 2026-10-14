package fr.javaquest.entity.equipments;

import java.awt.*;

public enum Rarity {
    COMMON("Common",Color.gray, 1.0F),
    UNCOMMON("Uncommon", Color.green, 1.1F),
    NORMAL("Normal", Color.blue, 1.25F),
    LEGEND("Legend", Color.yellow, 1.5F),
    EASTEREGG("EasterEgg", Color.black,1.0F)
    ;

    public final float multiplier;

    Rarity(String label, Color color, float multiplier) {
        this.multiplier = multiplier;
    }
}
