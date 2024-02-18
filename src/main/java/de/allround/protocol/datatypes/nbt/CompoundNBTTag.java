package de.allround.protocol.datatypes.nbt;

import java.util.ArrayList;
import java.util.List;

public final class CompoundNBTTag extends NBTTag {
    private final List<NBTTag> value;

    public CompoundNBTTag(String name, List<NBTTag> value) {
        super(name);
        this.value = value;
    }

    public CompoundNBTTag(String name) {
        this(name, new ArrayList<>());
    }

    public CompoundNBTTag() {
        this("", new ArrayList<>());
    }

    public List<NBTTag> getValue() {
        return value;
    }

    public byte getType() {
        return 10;
    }


}
