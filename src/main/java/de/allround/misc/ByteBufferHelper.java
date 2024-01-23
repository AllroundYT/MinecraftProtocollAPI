package de.allround.misc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferHelper {
    public static ByteBuffer combine(ByteBuffer... buffers){
        if (buffers == null) return ByteBuffer.allocate(0);
        List<Byte> bytes = new ArrayList<>();
        for (ByteBuffer buffer : buffers) {
            while (buffer.hasRemaining()){
                bytes.add(buffer.get());
            }
        }
        byte[] byteArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            byteArray[i] = bytes.get(i);
        }
        return ByteBuffer.allocate(bytes.size()).put(0, byteArray);
    }
}
