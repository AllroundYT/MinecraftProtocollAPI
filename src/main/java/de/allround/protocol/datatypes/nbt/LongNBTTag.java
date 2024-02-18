package de.allround.protocol.datatypes.nbt;

public final class LongNBTTag extends NBTTag {
    private final long value;

    public LongNBTTag(String name, long value) {
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
