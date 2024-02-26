package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record AcknowledgeConfiguration() implements ReadablePacket {
    @Override
    public int getID() {
        return 0x0B;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return this;
    }
}
