package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

import java.util.UUID;

public record TeleportToEntity(UUID targetPlayer) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x34;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new TeleportToEntity(buffer.readUUID());
    }
}
