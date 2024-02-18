package de.allround.protocol.packets.login.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record LoginStart(String name, UUID playerUUID) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x00;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new LoginStart(buffer.readString(), buffer.readUUID());
    }
}
