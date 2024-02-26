package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record PlayerAction(int status, Position location, byte face, int sequence) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x21;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new PlayerAction(buffer.readVarInt(), buffer.readPosition(), buffer.readByte(), buffer.readVarInt());
    }
}
