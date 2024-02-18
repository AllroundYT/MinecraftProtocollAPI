package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Tag;
import de.allround.protocol.packets.WritablePacket;

import java.util.List;

public record UpdateTags(List<Tag> tags) implements WritablePacket {
    @Override
    public int getID() {
        return 0x09;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer buffer = new ByteBuffer().write(tags.size());
        tags.forEach(buffer::write);
        return buffer;
    }
}
