package de.allround.protocol.packets.play.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record BundleDelimiter() implements WritablePacket {
    @Override
    public int getID() {
        return 0x00;
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull ByteBuffer write() {
        return new ByteBuffer();
    }
}
