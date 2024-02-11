package de.allround.protocol.datatypes.nbt;

public final class ShortTag extends Tag {
    private final short value;

    public ShortTag(String name, short value) {
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
