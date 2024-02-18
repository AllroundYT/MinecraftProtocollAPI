package de.allround.protocol.datatypes.nbt;

public final class ByteNBTTag extends NBTTag {
    private final byte value;

    public ByteNBTTag(String name, byte value) {
        super(name);
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public byte getType() {
        return 1;
    }

}
