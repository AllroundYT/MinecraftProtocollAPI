package de.allround.protocol.datatypes.nbt;

public final class StringNBTTag extends NBTTag {
    private final String value;

    public StringNBTTag(String name, String value) {
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
