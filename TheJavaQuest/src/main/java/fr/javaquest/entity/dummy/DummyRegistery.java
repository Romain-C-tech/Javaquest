package fr.javaquest.entity.dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Récupération des mannequins qui sont stockées sous format Json.
 * <p>
 *     Gestion d'erreur si le mannequin demandée n'est pas dans le fichier Json.
 * </p>
 * @author ssamepi
 */
public final class DummyRegistery {
    private static final Map<String, DummyData> DUMMY = new HashMap<>();
    private DummyRegistery(){}

    /**
     * Chargement des mannequins.
     * <p>
     *     Récupération des mannequins stockées dans le fichier Json.
     *     Destruction des mannequins contenues dans une liste.
     *     Insertion des mannequins précédemment trouvées pour les insérées dans la liste avec leurs informations.
     * </p>
     */

    public static void load(){
        Json json = new Json();
        Array<DummyData> list = json.fromJson(
            Array.class, DummyData.class, Gdx.files.internal("data/dummy.json"));
        DUMMY.clear();
        for (DummyData d : list) DUMMY.put(d.id, d);
    }

    /**
     * Récupération du mannequin qui correspond à l'identifiant de l'image demandée.
     * @param id est le nom du mannequin.
     * @return renvois toutes les données associées au mannequin demandée.
     *
     * @author ssamepi
     */
    public static DummyData get(String id) {
        DummyData d = DUMMY.get(id);
        if (d == null) throw new IllegalArgumentException("Pas de mannequin trouvé");
        return d;
    }
}
