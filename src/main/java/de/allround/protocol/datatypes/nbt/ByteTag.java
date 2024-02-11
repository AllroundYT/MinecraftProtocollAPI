package de.allround.protocol.datatypes.nbt;

public final class ByteTag extends Tag {
    private final byte value;

    public ByteTag(String name, byte value) {
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
