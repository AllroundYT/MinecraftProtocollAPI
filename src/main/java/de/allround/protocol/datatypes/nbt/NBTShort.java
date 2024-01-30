package de.allround.protocol.datatypes.nbt;

import java.nio.ByteBuffer;

public class NBTShort extends NBTTag<Short> {
    public NBTShort(Short data) {
        super(data);
    }

    @Override
    public ByteBuffer write() {
        return null;
    }

    @Override
    public NBTTag<Short> read(ByteBuffer buffer) {
        return null;
    }
}
