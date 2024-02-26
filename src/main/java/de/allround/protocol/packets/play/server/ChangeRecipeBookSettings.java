package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ChangeRecipeBookSettings(int bookId, boolean bookOpen, boolean filter) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x25;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ChangeRecipeBookSettings(buffer.readVarInt(), buffer.readBoolean(), buffer.readBoolean());
    }
}
