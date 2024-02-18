package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

public record Ping(int id) implements WritablePacket {
    @Override
    public int getID() {
        return 0x04;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(id);
    }
}
