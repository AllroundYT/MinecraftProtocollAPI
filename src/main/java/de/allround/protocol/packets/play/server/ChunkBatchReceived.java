package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ChunkBatchReceived(float chunksPerTick) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x07;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ChunkBatchReceived(buffer.readFloat());
    }
}
