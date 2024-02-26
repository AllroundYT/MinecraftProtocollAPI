package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public record ChatMessage(String message,
                          long timestamp,
                          long salt,
                          byte[] signature,
                          int messageCount,
                          BitSet acknowledged
) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        String msg = buffer.readString(256);
        long timestamp = buffer.readLong();
        long salt = buffer.readLong();
        boolean hasSignature = buffer.readBoolean();
        byte[] signature = buffer.readArray(256);
        int msgCount = buffer.readVarInt();
        BitSet acknowledged = buffer.readBitSet();
        return new ChatMessage(
                msg,
                timestamp,
                salt,
                hasSignature ? signature : null,
                msgCount,
                acknowledged
        );
    }
}
