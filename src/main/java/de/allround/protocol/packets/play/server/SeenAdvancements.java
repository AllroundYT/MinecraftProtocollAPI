package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.ReadablePacket;

public record SeenAdvancements(int action, Identifier tabId) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x29;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        int action = buffer.readVarInt();
        if (action == 0) {
            return new SeenAdvancements(action, buffer.readIdentifier());
        } else {
            return new SeenAdvancements(action, null);
        }
    }
}
