package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetPlayerOnGround(boolean onGround) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x1A;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetPlayerOnGround(buffer.readBoolean());
    }
}
