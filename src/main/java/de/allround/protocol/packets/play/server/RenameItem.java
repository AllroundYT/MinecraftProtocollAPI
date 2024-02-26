package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record RenameItem(String itemName) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x27;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new RenameItem(buffer.readString());
    }
}
