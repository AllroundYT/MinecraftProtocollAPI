package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record ProgramCommandBlock(Position location, String command, int mode, byte flags) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2D;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ProgramCommandBlock(buffer.readPosition(), buffer.readString(), buffer.readVarInt(), buffer.readByte());
    }
}
