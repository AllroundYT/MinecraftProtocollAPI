package de.allround.protocol.packets.login.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

public record SetCompression(int threshold) implements WritablePacket {
    @Override
    public int getID() {
        return 0x03;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().writeVarInt(threshold);
    }
}
