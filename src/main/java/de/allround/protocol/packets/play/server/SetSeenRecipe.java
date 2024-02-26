package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.ReadablePacket;

public record SetSeenRecipe(Identifier recipeId) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x26;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetSeenRecipe(buffer.readIdentifier());
    }
}
