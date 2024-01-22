package de.allround.protocol.packets;

import java.nio.ByteBuffer;

public interface Packet {
    ByteBuffer write();
    void read(ByteBuffer buffer);
}
