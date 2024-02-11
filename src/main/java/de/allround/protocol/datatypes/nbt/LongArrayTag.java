package de.allround.protocol.datatypes.nbt;

public final class LongArrayTag extends Tag {
    private final long[] value;

    public LongArrayTag(String name, long[] value) {
        super(name);
        this.value = value;
    }

    public long[] getValue() {
        return value;
    }

    public byte getType() {
        return 12;
    }

}
