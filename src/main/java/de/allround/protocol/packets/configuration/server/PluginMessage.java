package de.allround.protocol.packets.configuration.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PluginMessage(Identifier channel, byte[] data) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x01;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new PluginMessage(buffer.readIdentifier(), buffer.readRemaining());
    }
}
