package de.allround.protocol.packets.configuration.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record FinishConfiguration() implements ReadablePacket {
    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return this;
    }
}
