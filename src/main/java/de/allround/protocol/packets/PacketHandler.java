package de.allround.protocol.packets;

@FunctionalInterface
public interface PacketHandler<PacketType extends Packet> {
    void handle(PacketType packetType);
}
