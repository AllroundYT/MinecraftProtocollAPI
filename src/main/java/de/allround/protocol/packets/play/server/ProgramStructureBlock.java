package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record ProgramStructureBlock(Position location,
                                    int action,
                                    int mode,
                                    String name,
                                    byte offsetX,
                                    byte offsetY,
                                    byte offsetZ,
                                    byte x,
                                    byte y,
                                    byte z,
                                    int mirror,
                                    int rotation,
                                    String metadata,
                                    float integrity,
                                    long seed,
                                    int flags
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x31;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ProgramStructureBlock(
                buffer.readPosition(),
                buffer.readVarInt(),
                buffer.readVarInt(),
                buffer.readString(),
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                buffer.readByte(),
                buffer.readVarInt(),
                buffer.readVarInt(),
                buffer.readString(),
                buffer.readFloat(),
                buffer.readLong(),
                buffer.readVarInt()
        );
    }
}
