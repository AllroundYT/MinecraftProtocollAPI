package de.allround.protocol.datatypes;

import de.allround.protocol.datatypes.nbt.NBTTag;

public record Slot(boolean present, Integer id, Byte count, NBTTag nbt) {
}
