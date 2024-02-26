package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetPlayerRotation(float yaw, float pitch, boolean onGround) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x19;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetPlayerRotation(buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
    }
}
