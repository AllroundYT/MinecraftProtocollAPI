package de.allround.protocol.packets;

import de.allround.player.PlayerConnection;
import de.allround.protocol.datatypes.DataType;
import org.jetbrains.annotations.NotNull;

public class PacketProcessor {

    private final PlayerConnection playerConnection;
    private boolean compression; //todo: compression

    public PacketProcessor setCompression(boolean compression) {
        this.compression = compression;
        return this;
    }

    public boolean isCompression() {
        return compression;
    }

    public PacketProcessor(@NotNull PlayerConnection playerConnection) {
        this.playerConnection = playerConnection;

        playerConnection.getClient().handle(buffer -> {
            if (!compression){
                DataType.VAR_INT.read(buffer);
                int packetType = DataType.VAR_INT.read(buffer);

                switch (playerConnection.getConnectionState()) {
                    case HANDSHAKE -> {
                        Packet packet = Packets.Serverbound.getHandshake(packetType, buffer);
                        playerConnection.handlePacket(packet);
                    }
                    case STATUS -> {
                        Packet packet = Packets.Serverbound.getStatus(packetType, buffer);
                        playerConnection.handlePacket(packet);
                    }

                    case LOGIN -> {
                        Packet packet = Packets.Serverbound.getLogin(packetType, buffer);
                        playerConnection.handlePacket(packet);
                    }
                    case PLAY -> {
                        Packet packet = Packets.Serverbound.getPlay(packetType, buffer);
                        playerConnection.handlePacket(packet);
                    }
                    default -> {}
                }
            }
        });
    }
}
