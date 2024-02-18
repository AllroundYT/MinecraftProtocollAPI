package de.allround.protocol.datatypes.nbt;

public final class IntArrayNBTTag extends NBTTag {
    private final int[] value;

    public IntArrayNBTTag(String name, int[] value) {
        super(name);
        this.value = value;
    }

    public int[] getValue() {
        return value;
    }

    public byte getType() {
        return 11;
    }

}

