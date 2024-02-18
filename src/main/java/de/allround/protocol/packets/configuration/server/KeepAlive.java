package de.allround.protocol.packets.configuration.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record KeepAlive(long id) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x03;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new KeepAlive(buffer.readLong());
    }
}
