package de.allround.protocol.packets.handshake.server;

import de.allround.protocol.ConnectionState;
import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


public record Handshake(int protocolVersion,String serverAddress,short serverPort,ConnectionState nextState) implements ReadablePacket {


    @Override
    public int getID() {
        return 0x00;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull Handshake read(@NotNull ByteBuffer buffer) {
        return new Handshake(
                buffer.readVarInt(),
                buffer.readString(),
                buffer.readShort(),
                ConnectionState.fromOrdinal(buffer.readVarInt())
        );
    }
}
