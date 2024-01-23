package de.allround.protocol.packets.status.server;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.Packet;

import java.nio.ByteBuffer;

public class PingRequest implements Packet {
    private long payload;

    public long getPayload() {
        return payload;
    }

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public ByteBuffer writeDataFields() {
        return DataType.LONG.write(payload);
    }

    @Override
    public Packet readDataFields(ByteBuffer buffer) {
        payload = DataType.LONG.read(buffer);
        return this;
    }
}
