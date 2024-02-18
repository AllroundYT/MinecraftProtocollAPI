package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.WritablePacket;

public record PluginMessage(Identifier channel, byte[] data) implements WritablePacket {
    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(channel).write(data);
    }
}
