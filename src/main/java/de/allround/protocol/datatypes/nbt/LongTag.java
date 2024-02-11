package de.allround.protocol.datatypes.nbt;

public final class LongTag extends Tag {
    private final long value;

    public LongTag(String name, long value) {
        super(name);
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public byte getType() {
        return 4;
    }

}
