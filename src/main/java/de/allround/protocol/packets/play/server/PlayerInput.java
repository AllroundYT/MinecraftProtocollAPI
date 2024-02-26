package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record PlayerInput(float sideways, float forwards, int flags) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x23;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PlayerInput(buffer.readFloat(), buffer.readFloat(), buffer.readByte() & 0xFF);
    }
}
