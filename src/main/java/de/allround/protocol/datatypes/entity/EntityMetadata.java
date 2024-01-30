package de.allround.protocol.datatypes.entity;

import de.allround.protocol.datatypes.entity.entries.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntityMetadata {
    private final List<Entry<?>> entries;

    public EntityMetadata(Entry<?>... entries) {
        if (entries == null){
            this.entries = new ArrayList<>();
        } else  {
            this.entries = new ArrayList<>(List.of(entries));
        }
    }

    public EntityMetadata addEntry(Entry<?> entry){
        entries.add(entry);
        return this;
    }

    public EntityMetadata(List<Entry<?>> entries) {
        this.entries = entries;
    }

    public List<Entry<?>> getEntries() {
        return entries;
    }

    public Entry<?>[] getEntryArray(){
        return entries.toArray(new Entry[0]);
    }
}
