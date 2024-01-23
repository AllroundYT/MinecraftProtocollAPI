package de.allround.protocol.packets.status.server;

import de.allround.protocol.packets.Packet;

import java.nio.ByteBuffer;

public class StatusRequest implements Packet {
    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer writeDataFields() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public Packet readDataFields(ByteBuffer buffer) {
        return this;
    }
}
