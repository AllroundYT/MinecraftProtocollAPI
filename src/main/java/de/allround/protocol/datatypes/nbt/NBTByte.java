package de.allround.protocol.datatypes.nbt;

import java.nio.ByteBuffer;

public class NBTByte extends NBTTag<Byte> {
    public NBTByte(Byte data) {
        super(data);
    }

    @Override
    public ByteBuffer write() {
        return null;
    }

    @Override
    public NBTTag<Byte> read(ByteBuffer buffer) {
        return null;
    }
}
