package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Identifier;
import de.allround.protocol.packets.WritablePacket;

import java.util.List;

public record FeatureFlags(List<Identifier> features) implements WritablePacket {
    @Override
    public int getID() {
        return 0x08;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer buffer = new ByteBuffer().write(features.size());
        features.forEach(buffer::write);
        return buffer;
    }
}
