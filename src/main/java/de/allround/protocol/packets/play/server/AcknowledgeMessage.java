package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record AcknowledgeMessage(int messageCount) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x03;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new AcknowledgeMessage(buffer.readVarInt());
    }
}
