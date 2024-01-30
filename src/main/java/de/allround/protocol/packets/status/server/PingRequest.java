package de.allround.protocol.packets.status.server;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.ReadablePacket;

import java.nio.ByteBuffer;

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
    public ReadablePacket read(ByteBuffer buffer) {
        payload = DataType.LONG.read(buffer);
        return this;
    }
}
