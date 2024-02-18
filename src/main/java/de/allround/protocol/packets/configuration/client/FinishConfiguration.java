package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

public record FinishConfiguration() implements WritablePacket {
    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer();
    }
}
