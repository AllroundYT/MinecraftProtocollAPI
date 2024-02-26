package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.ReadablePacket;

public record PlaceRecipe(byte windowId, Identifier recipe, boolean makeAll) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x1F;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PlaceRecipe(buffer.readByte(), buffer.readIdentifier(), buffer.readBoolean());
    }
}
