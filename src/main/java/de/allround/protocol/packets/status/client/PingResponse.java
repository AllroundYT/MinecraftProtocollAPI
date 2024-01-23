package de.allround.protocol.packets.status.client;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.Packet;

import java.nio.ByteBuffer;

public class PingResponse implements Packet {

    private long payload;

    public PingResponse(long payload) {
        this.payload = payload;
    }

    public PingResponse() {
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
