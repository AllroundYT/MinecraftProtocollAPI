package de.allround.protocol.packets.status.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public class StatusRequest implements ReadablePacket {
    @Override
    public int getID() {
        return 0x00;
    }


    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return this;
    }
}
