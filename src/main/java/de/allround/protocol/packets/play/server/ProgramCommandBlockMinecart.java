package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ProgramCommandBlockMinecart(int entityId, String command, boolean trackOutput) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2E;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ProgramCommandBlockMinecart(buffer.readVarInt(), buffer.readString(), buffer.readBoolean());
    }
}
