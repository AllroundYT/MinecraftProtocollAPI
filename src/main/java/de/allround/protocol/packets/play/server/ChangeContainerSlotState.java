package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

public record ChangeContainerSlotState(int slotId, int windowId, boolean state) implements ReadablePacket {

    @Override
    public int getID() {
        return 0x0F;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ChangeContainerSlotState(buffer.readVarInt(), buffer.readVarInt(), buffer.readBoolean());
    }
}
