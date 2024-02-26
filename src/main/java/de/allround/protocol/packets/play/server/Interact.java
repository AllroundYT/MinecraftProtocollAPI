package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record Interact(int entityId, int type, float x, float y, float z, int hand, boolean sneaking
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x13;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        if (type == 2) {
            return new Interact(
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readFloat(),
                    buffer.readVarInt(),
                    buffer.readBoolean()
            );
        }
        return new Interact(buffer.readVarInt(), buffer.readVarInt(), 0, 0, 0, 0, buffer.readBoolean());
    }
}
