package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record JigsawGenerate(Position location, int levels, boolean keepJigsaws) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x14;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new JigsawGenerate(buffer.readPosition(), buffer.readVarInt(), buffer.readBoolean());
    }
}
