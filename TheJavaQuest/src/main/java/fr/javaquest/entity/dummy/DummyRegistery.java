package fr.javaquest.entity.dummy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

public final class DummyRegistery {
    private static final Map<String, DummyData> DUMMY = new HashMap<>();
    private DummyRegistery(){}

    public static void load(){
        Json json = new Json();
        Array<DummyData> list = json.fromJson(
            Array.class, DummyData.class, Gdx.files.internal("data/dummy.json"));
        DUMMY.clear();
        for (DummyData d : list) DUMMY.put(d.id, d);
    }
    public static DummyData get(String id) {
        DummyData d = DUMMY.get(id);
        if (d == null) throw new IllegalArgumentException("Pas de mannequin trouvé");
        return d;
    }
}
