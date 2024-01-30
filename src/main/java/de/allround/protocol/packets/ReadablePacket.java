package de.allround.protocol.packets;

import java.nio.ByteBuffer;

public interface ReadablePacket extends Packet {
    ReadablePacket read(ByteBuffer buffer);
}
