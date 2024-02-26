package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record ProgramJigsawBlock(Position location,
                                 Identifier name,
                                 Identifier target,
                                 Identifier pool,
                                 String finalState,
                                 String joinType,
                                 int selectionPriority,
                                 int PlacementPriority
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x30;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ProgramJigsawBlock(
                buffer.readPosition(),
                buffer.readIdentifier(),
                buffer.readIdentifier(),
                buffer.readIdentifier(),
                buffer.readString(),
                buffer.readString(),
                buffer.readVarInt(),
                buffer.readVarInt()
        );
    }
}
