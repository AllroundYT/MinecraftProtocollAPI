package de.allround.protocol.datatypes.nbt;

public final class DoubleNBTTag extends NBTTag {
    private final double value;

    public DoubleNBTTag(String name, double value) {
        super(name);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public byte getType() {
        return 6;
    }

}
