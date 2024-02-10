package de.allround.protocol.datatypes.nbt;


import de.allround.protocol.datatypes.ByteBuffer;

import java.util.List;

public interface NBTTag<Type> {

    NBTTag<Void> END = new NBTTagImpl<>(0, _ -> new ByteBuffer(), _ -> null);
    NBTTag<Byte> BYTE = new NBTTagImpl<>(1, new ByteBuffer()::write, ByteBuffer::read);
    NBTTag<Short> SHORT = new NBTTagImpl<>(2, new ByteBuffer()::writeShort, ByteBuffer::readShort);

    NBTTag<Integer> INTEGER = new NBTTagImpl<>(3, new ByteBuffer()::writeInteger, ByteBuffer::readInteger);
    List<NBTTag<?>> VALUES = List.of(END, BYTE, SHORT);

    @SuppressWarnings("unchecked")
    static <T> NBTTag<T> forId(byte id) {
        return (NBTTag<T>) VALUES.stream().filter(nbtTag -> (nbtTag.id() == id)).findFirst().orElse(END);
    }

    ByteBuffer write();

    NBTTag<Type> read(ByteBuffer buffer);

    Type data();

    NBTTag<Type> data(Type data);

    byte id();
}
