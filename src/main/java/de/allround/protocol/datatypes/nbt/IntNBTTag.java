package de.allround.protocol.datatypes.nbt;

public final class IntNBTTag extends NBTTag {
    private final int value;

    public IntNBTTag(String name, int value) {
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
