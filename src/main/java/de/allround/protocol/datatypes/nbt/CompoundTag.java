package de.allround.protocol.datatypes.nbt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CompoundTag extends Tag {
    private final List<Tag> value;

    public CompoundTag(String name, List<Tag> value) {
        super(name);
        this.value = value;
    }

    public List<Tag> getValue() {
        return value;
    }

    public byte getType() {
        return 10;
    }


}
