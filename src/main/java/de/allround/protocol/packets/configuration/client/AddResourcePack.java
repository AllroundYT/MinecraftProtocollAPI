package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Chat;
import de.allround.protocol.packets.WritablePacket;

import java.util.UUID;

public record AddResourcePack(UUID uuid,
                              String url,
                              String hash,
                              boolean forced,
                              boolean hasPromptMessage,
                              Chat promptMessage
) implements WritablePacket {
    @Override
    public int getID() {
        return 0x07;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer buffer = new ByteBuffer()
                .write(url)
                .write(url, 32767)

                .write(hash, 40)
                .write(forced)
                .write(hasPromptMessage);
        if (hasPromptMessage) buffer.write(promptMessage.toNBT());
        return buffer;
    }
}
