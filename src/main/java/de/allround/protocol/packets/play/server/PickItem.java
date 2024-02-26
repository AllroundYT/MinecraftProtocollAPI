package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record PickItem(int slotToUse) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x1D;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PickItem(buffer.readVarInt());
    }
}
