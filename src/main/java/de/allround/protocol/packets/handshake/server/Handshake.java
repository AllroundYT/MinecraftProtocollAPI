package de.allround.protocol.packets.handshake.server;

import de.allround.misc.ByteBufferHelper;
import de.allround.player.PlayerConnection;
import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.Packet;

import java.nio.ByteBuffer;

public class Handshake implements Packet {
    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private PlayerConnection.ConnectionState nextState;

    public PlayerConnection.ConnectionState getNextState() {
        return nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public short getServerPort() {
        return serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer writeDataFields() {
        return ByteBufferHelper.combine(
                DataType.VAR_INT.write(protocolVersion),
                DataType.STRING.write(serverAddress),
                DataType.SHORT.write(serverPort),
                DataType.VAR_INT.write(nextState.ordinal())
        );
    }

    @Override
    public Handshake readDataFields(ByteBuffer buffer) {
        protocolVersion = DataType.VAR_INT.read(buffer);
        serverAddress = DataType.STRING.read(buffer);
        serverPort = DataType.SHORT.read(buffer);
        nextState = PlayerConnection.ConnectionState.fromOrdinal(DataType.VAR_INT.read(buffer));
        return this;
    }
}
