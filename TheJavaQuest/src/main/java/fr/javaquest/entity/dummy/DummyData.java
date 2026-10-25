package fr.javaquest.entity.dummy;

import fr.javaquest.entity.HitboxSize;

/**
 * Tout les mannequins ont un identifiant, un nom, une zone de réception de dégats et un fichier où est stockée l'image.
 *
 * @author ssamepi
 */
public class DummyData{
    public String id;
    public String name;
    public HitboxSize hitbox;
    public String renderFile;

    public DummyData(){}
}
