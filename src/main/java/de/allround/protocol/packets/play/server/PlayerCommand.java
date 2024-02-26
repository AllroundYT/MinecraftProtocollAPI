package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record PlayerCommand(int entityId, int actionId, int jumpBoost) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x22;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PlayerCommand(buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }
}
