package de.allround.protocol.datatypes.particles;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class Particle{

    public abstract ByteBuffer write();
    public abstract void read(ByteBuffer buffer);

    private final Identifier name;
    private final byte id;
    private final DataType[] dataTypes;

    protected Particle(Identifier name, byte id, DataType[] dataTypes) {
        this.name = name;
        this.id = id;
        this.dataTypes = dataTypes;
    }

    protected final @NotNull ByteBuffer _write(Object @NotNull [] data){
        List<byte[]> dataAsArrays = new ArrayList<>();
        for (int i = 0; i < data.length && i < dataTypes.length; i++) {
            dataAsArrays.add(dataTypes[i].write(data[i]).array());
        }
        int allocationSize = dataAsArrays.stream().mapToInt(value -> value.length).sum();
        ByteBuffer buffer = ByteBuffer.allocate(1 + allocationSize);
        buffer.put(0,id);
        for (int i = 0; i < allocationSize;) {
            byte[] array = dataAsArrays.get(i);
            buffer.put(i + 1, array);
            i += array.length;
        }
        return buffer;
    }

    @Contract(pure = true)
    protected final Object @Nullable [] _read(ByteBuffer buffer){
        Object[] resultData = new Object[dataTypes.length];
        for (int i = 0; i < dataTypes.length; i++) {
            resultData[i] = dataTypes[i].read(buffer);
        }
        return resultData;
    }
}
