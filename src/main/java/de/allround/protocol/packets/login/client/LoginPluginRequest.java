package de.allround.protocol.packets.login.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.WritablePacket;

public record LoginPluginRequest(int messageId, Identifier channel, byte[] data) implements WritablePacket {

    @Override
    public int getID() {
        return 0x04;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer()
                .writeVarInt(messageId)
                .write(channel)
                .write(data);
    }
}
