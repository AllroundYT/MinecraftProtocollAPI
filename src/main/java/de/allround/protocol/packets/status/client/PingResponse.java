package de.allround.protocol.packets.status.client;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.WritablePacket;

import java.nio.ByteBuffer;

public class PingResponse implements WritablePacket {

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
    public ByteBuffer write() {
        return DataType.LONG.write(payload);
    }

}
