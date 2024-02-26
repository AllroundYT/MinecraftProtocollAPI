package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetHeldItem(short slot) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2C;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetHeldItem(buffer.readShort());
    }
}
