package de.allround.protocol.datatypes.entity.entries;

import de.allround.misc.ByteBufferHelper;
import de.allround.protocol.datatypes.DataType;

import java.nio.ByteBuffer;

public abstract class Entry<T> {
    private final byte index;
    private final EntryType<T> entryType;
    private final T value;

    protected Entry(byte index, EntryType<T> entryType, T value) {
        this.index = index;
        this.entryType = entryType;
        this.value = value;
    }

    public ByteBuffer write() {
        return ByteBufferHelper.combine(
                DataType.BYTE.write(index),
                DataType.VAR_INT.write(entryType.getId()),
                entryType.write(value)
        );
    }
}