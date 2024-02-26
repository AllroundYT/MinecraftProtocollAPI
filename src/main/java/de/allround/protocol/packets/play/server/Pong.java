package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record Pong(int id) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x24;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new Pong(buffer.readInteger());
    }
}
