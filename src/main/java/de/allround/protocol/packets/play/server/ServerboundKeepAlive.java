package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ServerboundKeepAlive(long keepAlive) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x15;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ServerboundKeepAlive(buffer.readLong());
    }
}
