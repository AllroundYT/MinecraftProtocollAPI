package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Chat;
import de.allround.protocol.packets.WritablePacket;

public record Disconnect(Chat reason) implements WritablePacket {

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(reason.toNBT());
    }
}
