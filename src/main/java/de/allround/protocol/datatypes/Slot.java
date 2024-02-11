package de.allround.protocol.datatypes;

import de.allround.protocol.datatypes.nbt.Tag;

public record Slot(boolean present, Integer id, Byte count, Tag nbt) {
}
