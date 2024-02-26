package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record UseItem(int hand, int sequence) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x36;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(ByteBuffer buffer) {
        return new UseItem(buffer.readVarInt(), buffer.readVarInt());
    }
}
