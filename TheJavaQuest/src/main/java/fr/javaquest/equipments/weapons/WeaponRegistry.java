package fr.javaquest.equipments.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public final class WeaponRegistry {
    private static final Map<String, WeaponData> WEAPONS = new HashMap<>();

    private WeaponRegistry() {}

    @SuppressWarnings("unchecked")
    public static void load() {
        Json json = new Json();
        Array<WeaponData> list = json.fromJson(
                Array.class, WeaponData.class,
                Gdx.files.internal("data/weapons.json"));

        WEAPONS.clear();
        for (WeaponData data : list) {
            WEAPONS.put(data.id, data);
        }
    }

    public static WeaponData get(String id) {
        WeaponData data = WEAPONS.get(id);
        if (data == null) {
            throw new IllegalArgumentException("Arme inconnue : " + id);
        }
        return data;
    }
}