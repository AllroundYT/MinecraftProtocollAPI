package de.allround.protocol.datatypes.nbt;

public final class IntTag extends Tag {
    private final int value;

    public IntTag(String name, int value) {
        super(name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public byte getType() {
        return 3;
    }

}
