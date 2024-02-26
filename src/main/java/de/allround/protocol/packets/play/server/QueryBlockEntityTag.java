package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record QueryBlockEntityTag(int transactionId, Position location) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x01;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new QueryBlockEntityTag(buffer.readVarInt(), buffer.readPosition());
    }
}
