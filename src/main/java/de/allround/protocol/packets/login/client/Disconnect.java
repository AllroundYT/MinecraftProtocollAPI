package de.allround.protocol.packets.login.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Chat;
import de.allround.protocol.packets.WritablePacket;

public record Disconnect(Chat chat) implements WritablePacket {

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(chat.toJson());
    }
}
