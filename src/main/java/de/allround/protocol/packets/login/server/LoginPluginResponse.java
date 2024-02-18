package de.allround.protocol.packets.login.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record LoginPluginResponse(int messageId, boolean successful, byte[] optionalData) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x02;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        int messageId = buffer.readVarInt();
        boolean success = buffer.readBoolean();
        if (success){
            return new LoginPluginResponse(messageId, successful, buffer.readRemaining());
        }
        return new LoginPluginResponse(messageId, successful, new byte[0]);
    }
}
