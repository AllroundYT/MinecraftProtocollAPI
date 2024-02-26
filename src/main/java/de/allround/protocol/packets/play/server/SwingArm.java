package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SwingArm(int hand) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x33;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SwingArm(buffer.readVarInt());
    }
}
