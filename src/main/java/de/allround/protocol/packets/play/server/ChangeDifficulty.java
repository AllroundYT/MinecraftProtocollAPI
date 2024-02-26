package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ChangeDifficulty(byte newDifficulty) implements ReadablePacket {


    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ChangeDifficulty(buffer.readByte());
    }
}
