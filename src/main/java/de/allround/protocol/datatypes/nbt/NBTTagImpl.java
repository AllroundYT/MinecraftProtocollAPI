package de.allround.protocol.datatypes.nbt;

import de.allround.protocol.datatypes.ByteBuffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

class NBTTagImpl<Type> implements NBTTag<Type> {

    private final int id;
    private final Function<Type, ByteBuffer> writer;
    private final Function<ByteBuffer, Type> reader;
    private Type data;


    public NBTTagImpl(Type data, int id, Function<Type, ByteBuffer> writer, Function<ByteBuffer, Type> reader) {
        this.data = data;
        this.id = id;
        this.writer = writer;
        this.reader = reader;
    }

    public NBTTagImpl(int id, Function<Type, ByteBuffer> writer, Function<ByteBuffer, Type> reader) {
        this(null, id, writer, reader);
    }

    public NBTTagImpl<Type> data(Type data) {
        this.data = data;
        return this;
    }

    @Override
    public ByteBuffer write() {
        return writer.apply(data);
    }

    @Contract("_ -> new")
    @Override
    public @NotNull NBTTag<Type> read(ByteBuffer buffer) {
        return new NBTTagImpl<>(reader.apply(buffer), id, writer, reader);
    }

    @Override
    public Type data() {
        return data;
    }

    @Override
    public byte id() {
        return (byte) id;
    }
}