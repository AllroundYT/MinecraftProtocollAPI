package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record CommandSuggestionsRequest(int transactionId, String text) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x0A;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new CommandSuggestionsRequest(buffer.readVarInt(), buffer.readString());
    }
}
