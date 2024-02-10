package de.allround.protocol.packets.status.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.NotNull;


public class PingRequest implements ReadablePacket {
    private long payload;

    public long getPayload() {
        return payload;
    }

    @Override
    public int getID() {
        return 0x01;
    }


    @Override
    public ReadablePacket read(@NotNull ByteBuffer buffer) {
        payload = buffer.readLong();
        return this;
    }
}
