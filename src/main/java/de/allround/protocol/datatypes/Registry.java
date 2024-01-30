package de.allround.protocol.datatypes;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Registry<DataType> {

    private final Map<Identifier, DataType> entries;


    public Registry() {
        this.entries = new ConcurrentHashMap<>();
        init();
    }

    protected void entry(Identifier identifier, DataType value) {
        entries.put(identifier, value);
    }

    protected void entry(String identifier, DataType value) {
        entries.put(Identifier.minecraft(identifier), value);
    }

    protected abstract void init();

    public Set<Map.Entry<Identifier, DataType>> getEntries() {
        return entries.entrySet();
    }

    public DataType get(Identifier identifier) {
        return entries.get(identifier);
    }

    public DataType get(String identifier) {
        return entries.get(Identifier.minecraft(identifier));
    }

    public boolean has(String identifier) {
        return entries.containsKey(Identifier.minecraft(identifier));
    }

    public boolean has(Identifier identifier) {
        return entries.containsKey(identifier);
    }
}
