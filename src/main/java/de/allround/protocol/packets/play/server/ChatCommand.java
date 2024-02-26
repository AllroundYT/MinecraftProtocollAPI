package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public record ChatCommand(String command, long timestamp, long salt, ArgumentSignature[] argumentSignatures, int messageCount, BitSet acknowledged) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x04;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        String command = buffer.readString();
        long timestamp = buffer.readLong();
        long salt = buffer.readLong();
        ArgumentSignature[] argumentSignatures = new ArgumentSignature[buffer.readVarInt()];
        for (int i = 0; i < argumentSignatures.length; i++) {
            argumentSignatures[i] = new ArgumentSignature(buffer.readString(), buffer.readArray(256));
        }
        int messageCount = buffer.readVarInt();
        BitSet bitSet = buffer.readBitSet();
        return new ChatCommand(
                command,
                timestamp,
                salt,
                argumentSignatures,
                messageCount,
                bitSet
        );
    }

    public record ArgumentSignature(String name, byte[] signature) {
    }
}
