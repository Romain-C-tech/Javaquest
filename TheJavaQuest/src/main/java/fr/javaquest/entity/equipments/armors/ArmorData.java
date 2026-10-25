package fr.javaquest.entity.equipments.armors;

/**
 * Toutes les armures possèdent des caractéristiques communes.
 * <p>
 *     Toutes possèdent un identifiant, un nom, sont une pièce d'armure, donne des points de vie.
 * </p>
 *
 * @author Romain-C-tech
 */
public class ArmorData {
    public String id;
    public String name;
    public ArmorSlot slot;
    public int hp;

    public ArmorData() {}
}
