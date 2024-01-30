package de.allround.protocol.datatypes.nbt;

import java.nio.ByteBuffer;

public class NBTEnd extends NBTTag<Void> {
    public NBTEnd() {
        super(null);
    }

    @Override
    public ByteBuffer write() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public NBTTag<Void> read(ByteBuffer buffer) {
        return this;
    }
}
