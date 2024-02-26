package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

import java.util.UUID;

public record ResourcePackResponse(UUID uuid, int result) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x28;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ResourcePackResponse(buffer.readUUID(), buffer.readVarInt());
    }
}
