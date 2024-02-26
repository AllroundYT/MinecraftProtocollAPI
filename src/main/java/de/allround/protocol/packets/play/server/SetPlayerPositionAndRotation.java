package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetPlayerPositionAndRotation(double x,
                                           double y,
                                           double z,
                                           float yaw,
                                           float pitch,
                                           boolean onGround
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x18;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetPlayerPositionAndRotation(buffer.readDouble(),
                                                buffer.readDouble(),
                                                buffer.readDouble(),
                                                buffer.readFloat(),
                                                buffer.readFloat(),
                                                buffer.readBoolean()
        );
    }
}
