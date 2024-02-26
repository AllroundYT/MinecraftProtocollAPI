package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ClickContainerButton(byte windowId, byte buttonId) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x0C;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ClickContainerButton(buffer.readByte(), buffer.readByte());
    }
}
