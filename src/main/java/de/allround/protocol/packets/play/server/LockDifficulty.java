package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record LockDifficulty(boolean locked) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x16;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new LockDifficulty(buffer.readBoolean());
    }
}
