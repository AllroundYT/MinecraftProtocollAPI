package de.allround.protocol.packets;

import de.allround.protocol.datatypes.ByteBuffer;

public interface ReadablePacket extends Packet {
    ReadablePacket read(ByteBuffer buffer);
}
