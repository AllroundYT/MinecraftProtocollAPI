package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

import java.util.UUID;

public record RemoveResourcePack(boolean hasUUID, UUID uuid) implements WritablePacket {
    @Override
    public int getID() {
        return 0x06;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer buffer = new ByteBuffer().write(hasUUID);
        if (hasUUID) buffer.write(uuid);
        return buffer;
    }
}
