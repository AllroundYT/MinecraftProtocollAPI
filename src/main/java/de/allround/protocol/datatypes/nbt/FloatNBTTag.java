package de.allround.protocol.datatypes.nbt;

public final class FloatNBTTag extends NBTTag {
    private final float value;

    public FloatNBTTag(String name, float value) {
        super(name);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public byte getType() {
        return 5;
    }

}
