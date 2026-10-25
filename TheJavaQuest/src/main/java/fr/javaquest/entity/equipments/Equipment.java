package fr.javaquest.entity.equipments;

import fr.javaquest.entity.equipments.Rarity;

/**
 * Tout les équipements ont un nom et une rareté.
 *
 * @author Romain-C-tech
 */
public interface Equipment {
    String getName();
    Rarity getRarity();
}
