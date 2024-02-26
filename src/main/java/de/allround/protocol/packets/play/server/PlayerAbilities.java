package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record PlayerAbilities(byte flags) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x20;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PlayerAbilities(buffer.readByte());
    }
}
