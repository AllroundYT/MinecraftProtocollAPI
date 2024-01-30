package de.allround.protocol.datatypes.entity.entries;

import java.nio.ByteBuffer;

public interface EntryType<Type> {
    int getId();

    ByteBuffer write(Type type);

    Type read(ByteBuffer buffer);

}