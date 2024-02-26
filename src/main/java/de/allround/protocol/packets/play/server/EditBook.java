package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record EditBook(int slot, String[] entries, String title) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x11;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        int slot = buffer.readVarInt();
        int count = buffer.readVarInt();
        String[] entries = new String[count];
        for (int i = 0; i < count; i++) {
            entries[i] = buffer.readString();
        }
        if (buffer.readBoolean()) {
            return new EditBook(slot, entries, buffer.readString());
        }
        return new EditBook(slot, entries, null);
    }
}
