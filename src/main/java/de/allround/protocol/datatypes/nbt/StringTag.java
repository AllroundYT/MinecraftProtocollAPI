package de.allround.protocol.datatypes.nbt;

public final class StringTag extends Tag {
    private final String value;

    public StringTag(String name, String value) {
        super(name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public byte getType() {
        return 8;
    }

}
