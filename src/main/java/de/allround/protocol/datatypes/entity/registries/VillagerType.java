package de.allround.protocol.datatypes.entity.registries;

import de.allround.protocol.datatypes.Registry;

public class VillagerType extends Registry<Byte> {

    private static VillagerType _instance;

    public static VillagerType getInstance() {
        if (_instance == null) _instance = new VillagerType();
        return _instance;
    }

    @Override
    protected void init() {
        entry("desert", (byte) 0);
        entry("jungle", (byte) 1);
        entry("plains", (byte) 2);
        entry("savanna", (byte) 3);
        entry("snow", (byte) 4);
        entry("swamp", (byte) 5);
        entry("taiga", (byte) 6);
    }
}
