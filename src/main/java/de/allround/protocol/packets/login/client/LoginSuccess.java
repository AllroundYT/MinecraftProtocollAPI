package de.allround.protocol.packets.login.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

import java.util.UUID;

public record LoginSuccess(UUID uuid, String username, String name, String value, boolean isSigned, String signature) implements WritablePacket {

    @Override
    public int getID() {
        return 0x02;
    }

    @Override
    public ByteBuffer write() {
        int numberOfArguments = isSigned ? 4 : 3;
        //todo - checken ob das so richtig ist bzw. auf dc fragen
        ByteBuffer buffer = new ByteBuffer()
                .write(uuid)
                .write(username, 16)
                .writeVarInt(numberOfArguments)
                .write(name, 32767)
                .write(value, 32767)
                .write(isSigned);
        if (isSigned){
            buffer.write(signature, 32767);
        }
        return buffer;
    }
}
