package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetPlayerPosition(double x, double y, double z, boolean onGround) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x17;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetPlayerPosition(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readBoolean()
        );
    }
}
