package de.allround.protocol.datatypes.nbt;

public final class EndNBTTag extends NBTTag {
    public EndNBTTag() {
        super("");
    }

    public byte getType() {
        return 0;
    }

}
