package de.allround.protocol.datatypes.nbt;

public final class FloatTag extends Tag {
    private final float value;

    public FloatTag(String name, float value) {
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
