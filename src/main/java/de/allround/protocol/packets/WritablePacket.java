package de.allround.protocol.packets;


import de.allround.protocol.datatypes.ByteBuffer;

import java.util.zip.Deflater;

public interface WritablePacket extends Packet {
    ByteBuffer write();


    default ByteBuffer createBuffer() {
        ByteBuffer buffer = write();
        buffer.insertVarInt(0,getID());
        int length = buffer.getSize();
        buffer.insertVarInt(0, length);
        return buffer;
    }
}
