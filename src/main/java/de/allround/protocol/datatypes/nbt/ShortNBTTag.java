package de.allround.protocol.datatypes.nbt;

public final class ShortNBTTag extends NBTTag {
    private final short value;

    public ShortNBTTag(String name, short value) {
        super(name);
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public byte getType() {
        return 2;
    }

}
