package de.allround.protocol.packets;

import de.allround.misc.reflections.ClassAllocator;
import de.allround.protocol.ConnectionState;
import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.configuration.server.*;
import de.allround.protocol.packets.handshake.server.Handshake;
import de.allround.protocol.packets.login.server.EncryptionResponse;
import de.allround.protocol.packets.login.server.LoginAcknowledged;
import de.allround.protocol.packets.login.server.LoginPluginResponse;
import de.allround.protocol.packets.login.server.LoginStart;
import de.allround.protocol.packets.status.client.PingResponse;
import de.allround.protocol.packets.status.client.StatusResponse;
import de.allround.protocol.packets.status.server.PingRequest;
import de.allround.protocol.packets.status.server.StatusRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class ServerboundPackets {

    private static final Map<Integer, ReadablePacket> HANDSHAKE = new HashMap<>();
    private static final Map<Integer, ReadablePacket> STATUS = new HashMap<>();
    private static final Map<Integer, ReadablePacket> LOGIN = new HashMap<>();
    private static final Map<Integer, ReadablePacket> CONFIGURATION = new HashMap<>();

    private static final Map<Integer, ReadablePacket> PLAY = new HashMap<>();

    static {
        //todo: add methode durch die angabe eines packets austauschen aus dem dann alle ReadablePacket klassen geladen werden
        // scanner nutzen!
        add(ConnectionState.HANDSHAKE, Handshake.class);
        add(ConnectionState.STATUS, StatusRequest.class, PingRequest.class);
        add(ConnectionState.LOGIN, EncryptionResponse.class, LoginAcknowledged.class, LoginPluginResponse.class, LoginStart.class);
        add(ConnectionState.CONFIGURATION, ClientInformation.class, FinishConfiguration.class, KeepAlive.class, PluginMessage.class, Pong.class, ResourcePackResponse.class);
        add(ConnectionState.PLAY);
    }

    @SafeVarargs
    private static void add(@NotNull ConnectionState connectionState, Class<? extends ReadablePacket>... packets){
        Map<Integer, ReadablePacket> packetMap = switch (connectionState){
            case HANDSHAKE -> HANDSHAKE;
            case STATUS -> STATUS;
            case LOGIN -> LOGIN;
            case CONFIGURATION -> CONFIGURATION;
            case PLAY -> PLAY;
            default -> Collections.emptyMap();
        };

        for (Class<? extends ReadablePacket> packetClass : packets) {
            ReadablePacket packet = ClassAllocator.allocate(packetClass);
            if (packet == null) continue;
            packetMap.put(packet.getID(), packet);
        }
    }

    public static ReadablePacket get(@NotNull ConnectionState state, int id) {
        return switch (state) {
            case CLOSED -> null;
            case PLAY -> getPlay(id);
            case LOGIN -> getLogin(id);
            case CONFIGURATION -> getConfiguration(id);
            case STATUS -> getStatus(id);
            case HANDSHAKE -> getHandshake(id);
        };
    }

    public static ReadablePacket getConfiguration(int id, ByteBuffer buffer) {
        return CONFIGURATION.get(id).read(buffer);
    }

    public static ReadablePacket getConfiguration(int id) {
        return CONFIGURATION.get(id);
    }

    public static ReadablePacket get(ConnectionState state, int id, ByteBuffer buffer) {
        return get(state, id).read(buffer);
    }

    public static ReadablePacket getStatus(int id, ByteBuffer buffer) {
        return STATUS.get(id).read(buffer);
    }

    public static ReadablePacket getStatus(int id) {
        return STATUS.get(id);
    }

    public static ReadablePacket getHandshake(int id, ByteBuffer buffer) {
        return HANDSHAKE.get(id).read(buffer);
    }

    public static ReadablePacket getHandshake(int id) {
        return HANDSHAKE.get(id);
    }

    public static ReadablePacket getLogin(int id, ByteBuffer buffer) {
        return LOGIN.get(id).read(buffer);
    }

    public static ReadablePacket getLogin(int id) {
        return LOGIN.get(id);
    }

    public static ReadablePacket getPlay(int id, ByteBuffer buffer) {
        return PLAY.get(id).read(buffer);
    }

    public static ReadablePacket getPlay(int id) {
        return PLAY.get(id);
    }
}
