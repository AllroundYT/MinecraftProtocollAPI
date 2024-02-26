package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record UseItemOn(int hand,
                        Position location,
                        int face,
                        float cursorX,
                        float cursorY,
                        float cursorZ,
                        boolean insideBlock,
                        int sequence
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x35;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new UseItemOn(
                buffer.readVarInt(),
                buffer.readPosition(),
                buffer.readVarInt(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readBoolean(),
                buffer.readVarInt()
        );
    }
}
