package de.allround.player;

import de.allround.future.Future;
import de.allround.misc.JSON;
import de.allround.misc.PacketTypeHandlerMap;
import de.allround.network.Client;
import de.allround.protocol.packets.Packet;
import de.allround.protocol.packets.PacketProcessor;
import de.allround.protocol.packets.handshake.server.Handshake;
import de.allround.protocol.packets.status.client.PingResponse;
import de.allround.protocol.packets.status.client.StatusResponse;
import de.allround.protocol.packets.status.server.PingRequest;
import de.allround.protocol.packets.status.server.StatusRequest;
import org.jetbrains.annotations.NotNull;

public class PlayerConnection {


    private final Client client;
    private final PacketProcessor packetProcessor;
    private final PacketTypeHandlerMap packetHandlers; //todo: bessere lösung finden
    private ConnectionState connectionState = ConnectionState.HANDSHAKE;

    public Future<Void> sendPacket(@NotNull Packet packet){
        System.out.println("Sending Packet: " + packet.getClass().getSimpleName() + " -> " + JSON.toJson(packet));
        return client.write(packet.write().array()).map(integer -> null);
    }

    public <PacketType extends Packet> void handlePacket(@NotNull PacketType packetType){
        System.out.println("Handling Packet: " + packetType.getClass().getSimpleName() + " -> " + JSON.toJson(packetType));
        packetHandlers.get(packetType.getClass()).forEach(packetHandler -> {
            packetHandler.handle(packetType);
        });
    }

    public PlayerConnection(Client client) {
        this.client = client;
        this.packetHandlers = new PacketTypeHandlerMap();
        initDefaultPacketHandlers();
        this.packetProcessor = new PacketProcessor(this);
    }

    public void initDefaultPacketHandlers(){
        //todo
        packetHandlers.add(Handshake.class, handshake -> {
            this.connectionState = handshake.getNextState();
        });

        packetHandlers.add(StatusRequest.class, statusRequest -> {
            sendPacket(new StatusResponse(
                    "1.20.2",
                    763,
                    "§cHallo",
                    true,
                    true
            ));
        });

        packetHandlers.add(PingRequest.class, pingRequest -> {
            long payload = pingRequest.getPayload();
            sendPacket(new PingResponse(payload));
        });
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }

    public PlayerConnection setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public PacketProcessor getPacketProcessor() {
        return packetProcessor;
    }

    public enum ConnectionState {
        HANDSHAKE,
        STATUS,
        LOGIN,
        PLAY,
        CLOSED;

        public static ConnectionState fromOrdinal(int ordinal) {
            return switch (ordinal) {
                case 0 -> HANDSHAKE;
                case 1 -> STATUS;
                case 2 -> LOGIN;
                case 3 -> PLAY;
                default -> CLOSED;
            };
        }
    }
}
