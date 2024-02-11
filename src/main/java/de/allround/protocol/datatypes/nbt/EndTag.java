package de.allround.protocol.datatypes.nbt;

public final class EndTag extends Tag {
    public EndTag() {
        super("");
    }

    public byte getType() {
        return 0;
    }

}
