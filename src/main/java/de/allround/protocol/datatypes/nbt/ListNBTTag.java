package de.allround.protocol.datatypes.nbt;

import java.util.List;

public final class ListNBTTag extends NBTTag {
    private final byte tagId;
    private final List<NBTTag> value;

    public ListNBTTag(String name, byte tagId, List<NBTTag> value) {
        super(name);
        this.tagId = tagId;
        this.value = value;
    }

    public byte getTagId() {
        return tagId;
    }

    public List<NBTTag> getValue() {
        return value;
    }

    public byte getType() {
        return 9;
    }



}
