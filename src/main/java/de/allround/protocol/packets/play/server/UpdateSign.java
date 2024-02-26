package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Position;
import de.allround.protocol.packets.ReadablePacket;

public record UpdateSign(Position location, boolean isFrontText, String line1, String line2, String line3, String line4) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x32;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new UpdateSign(buffer.readPosition(), buffer.readBoolean(), buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString());
    }
}
