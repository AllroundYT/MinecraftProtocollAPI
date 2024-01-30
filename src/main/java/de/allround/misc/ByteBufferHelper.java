package de.allround.misc;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ByteBufferHelper {
    public static ByteBuffer combine(ByteBuffer... buffers) {
        if (buffers == null) return ByteBuffer.allocate(0);
        List<Byte> bytes = new ArrayList<>();
        for (ByteBuffer buffer : buffers) {
            if (buffer == null) continue;
            while (buffer.hasRemaining()) {
                bytes.add(buffer.get());
            }
        }
        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            byteArray[i] = bytes.get(i);
        }
        return ByteBuffer.allocate(bytes.size()).put(0, byteArray);
    }


    public static @NotNull ByteBuffer createBuffer(@NotNull List<Byte> byteList){
        ByteBuffer buffer = ByteBuffer.allocate(byteList.size());
        for (int i = 0; i < byteList.size(); i++) {
            buffer.put(i, byteList.get(i));
        }
        return buffer;
    }
}
