package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record ClientInformation(String locale,
                                byte viewDistance,
                                int chatMode,
                                boolean chatColors,
                                int displayedSkinParts,
                                int mainHand,
                                boolean enableTextFiltering,
                                boolean allowServerListing
) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x09;
    }

    @Contract("_ -> new")
    @Override
    public @NotNull ReadablePacket read(@NotNull ByteBuffer buffer) {
        return new ClientInformation(
                buffer.readString(),
                buffer.readByte(),
                buffer.readVarInt(),
                buffer.readBoolean(),
                buffer.readByte() & 0xff,
                buffer.readVarInt(),
                buffer.readBoolean(),
                buffer.readBoolean()
        );
    }
}
