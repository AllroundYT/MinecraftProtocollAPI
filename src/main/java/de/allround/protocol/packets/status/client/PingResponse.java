package de.allround.protocol.packets.status.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;


public record PingResponse(long payload) implements WritablePacket {

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(payload);
    }

}
