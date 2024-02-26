package de.allround.protocol.packets.play.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.Slot;
import de.allround.protocol.packets.ReadablePacket;

import java.util.Map;

public record ClickContainer(int windowId,
                             int stateId,
                             short slot,
                             byte button,
                             int mode,
                             Map.Entry<Short, Slot>[] changedSlots,
                             Slot carriedItems
) implements ReadablePacket {
    @Override
    public int getID() {
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ReadablePacket read(ByteBuffer buffer) {
        int windowId = buffer.readByte() & 0xFF;
        int stateId = buffer.readVarInt();
        short slot = buffer.readShort();
        byte button = buffer.readByte();
        int mode = buffer.readVarInt();

        Map.Entry<Short, Slot>[] changedSlots = new Map.Entry[buffer.readVarInt()];
        for (int i = 0; i < changedSlots.length; i++) {
            short key = buffer.readShort();
            Slot value = buffer.readSlot();
            changedSlots[i] = Map.entry(key, value);
        }

        Slot carriedItems = buffer.readSlot();
        return new ClickContainer(windowId, stateId, slot, button, mode, changedSlots, carriedItems);
    }
}
