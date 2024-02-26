package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Slot;
import de.allround.protocol.packets.ReadablePacket;

public record SetCreativeModeSlot(short slot, Slot clickedItem) implements ReadablePacket {
    @Override
    public int getID() {
        return 0x2F;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new SetCreativeModeSlot(buffer.readShort(), buffer.readSlot());
    }
}
