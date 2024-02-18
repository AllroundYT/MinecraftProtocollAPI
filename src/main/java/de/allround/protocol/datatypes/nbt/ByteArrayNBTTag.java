package de.allround.protocol.datatypes.nbt;

public final class ByteArrayNBTTag extends NBTTag {
    private final byte[] value;

    public ByteArrayNBTTag(String name, byte[] value) {
        super(name);
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public byte getType() {
        return 7;
    }

}
