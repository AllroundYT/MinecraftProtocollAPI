package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record PlayerSession(UUID id, long expiresAt, byte[] publicKey, byte[] keySignature) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x06;
    }

    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        UUID id = buffer.readUUID();
        long expiresAt = buffer.readLong();
        byte[] publicKey = buffer.readArray(buffer.readVarInt());
        byte[] keySignature = buffer.readArray(buffer.readVarInt());
        return new PlayerSession(id, expiresAt, publicKey, keySignature);
    }
}
