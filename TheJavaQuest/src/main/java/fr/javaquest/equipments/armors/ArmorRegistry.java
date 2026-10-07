package fr.javaquest.equipments.armors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public final class ArmorRegistry {
    private static final Map<String, ArmorData> ARMORS = new HashMap<>();

    private ArmorRegistry() {}

    @SuppressWarnings("unchecked")
    public static void load() {
        Json json = new Json();
        Array<ArmorData> list = json.fromJson(
                Array.class, ArmorData.class,
                Gdx.files.internal("data/armors.json"));

        ARMORS.clear();
        for (ArmorData data : list) {
            ARMORS.put(data.id, data);
        }
    }

    public static ArmorData get(String id) {
        ArmorData data = ARMORS.get(id);
        if (data == null) {
            throw new IllegalArgumentException("Armure inconnue : " + id);
        }
        return data;
    }
}