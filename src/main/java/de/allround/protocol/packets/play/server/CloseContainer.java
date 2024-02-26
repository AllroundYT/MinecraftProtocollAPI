package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record CloseContainer(int windowId) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x0E;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new CloseContainer(buffer.readByte() & 0xFF);
    }
}
