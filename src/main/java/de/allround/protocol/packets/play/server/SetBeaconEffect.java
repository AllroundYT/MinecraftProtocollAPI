package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record SetBeaconEffect(boolean haasPrimaryEffect, int primaryEffect, boolean hasSecondaryEffect, int secondaryEffect) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2B;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetBeaconEffect(buffer.readBoolean(), buffer.readVarInt(), buffer.readBoolean(), buffer.readVarInt());
    }
}
