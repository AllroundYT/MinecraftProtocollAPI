package de.allround.protocol.packets.login.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record LoginAcknowledged() implements ReadablePacket {
    @Override
    public int getID() {
        return 0x03;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return this;
    }
}
