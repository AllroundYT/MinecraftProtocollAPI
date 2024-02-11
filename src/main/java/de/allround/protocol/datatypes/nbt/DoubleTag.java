package de.allround.protocol.datatypes.nbt;

public final class DoubleTag extends Tag {
    private final double value;

    public DoubleTag(String name, double value) {
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
