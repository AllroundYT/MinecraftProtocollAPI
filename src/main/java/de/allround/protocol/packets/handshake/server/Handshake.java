package de.allround.protocol.packets.handshake.server;

import de.allround.protocol.ConnectionState;
import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.ReadablePacket;

import java.nio.ByteBuffer;

public class Handshake implements ReadablePacket {
    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private ConnectionState nextState;

    public ConnectionState getNextState() {
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
    public Handshake read(ByteBuffer buffer) {
        protocolVersion = DataType.VAR_INT.read(buffer);
        serverAddress = DataType.STRING.read(buffer);
        serverPort = DataType.SHORT.read(buffer);
        nextState = ConnectionState.fromOrdinal(DataType.VAR_INT.read(buffer));
        return this;
    }
}
