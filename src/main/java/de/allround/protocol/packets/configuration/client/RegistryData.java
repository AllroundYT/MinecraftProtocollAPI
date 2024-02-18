package de.allround.protocol.packets.configuration.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.nbt.CompoundNBTTag;
import de.allround.protocol.packets.WritablePacket;

public record RegistryData(CompoundNBTTag registryCodec) implements WritablePacket {
    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer().write(registryCodec);
    }
}
