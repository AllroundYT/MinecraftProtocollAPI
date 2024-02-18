package de.allround.protocol.datatypes.nbt;

public final class LongArrayNBTTag extends NBTTag {
    private final long[] value;

    public LongArrayNBTTag(String name, long[] value) {
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
