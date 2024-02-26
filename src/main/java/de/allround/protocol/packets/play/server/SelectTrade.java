package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SelectTrade(int selectedSlot)  implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2A;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SelectTrade(buffer.readVarInt());
    }
}
