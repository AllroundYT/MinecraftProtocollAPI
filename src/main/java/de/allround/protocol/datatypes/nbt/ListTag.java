package de.allround.protocol.datatypes.nbt;

import java.util.List;

public final class ListTag extends Tag {
    private final byte tagId;
    private final List<Tag> value;

    public ListTag(String name, byte tagId, List<Tag> value) {
        super(name);
        this.tagId = tagId;
        this.value = value;
    }

    public byte getTagId() {
        return tagId;
    }

    public List<Tag> getValue() {
        return value;
    }

    public byte getType() {
        return 9;
    }



}
