package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record MoveVehicle(double x, double y, double z, float yaw, float pitch) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x1B;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new MoveVehicle(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readFloat(), buffer.readFloat());
    }
}
