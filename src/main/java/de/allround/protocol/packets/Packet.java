package de.allround.protocol.packets;

import de.allround.misc.ByteBufferHelper;
import de.allround.protocol.datatypes.DataType;

import java.nio.ByteBuffer;

public interface Packet {

    int getID();
    ByteBuffer writeDataFields();

    default ByteBuffer write(){
        ByteBuffer buffer = writeDataFields();
        ByteBuffer id = DataType.VAR_INT.write(getID());
        byte[] dataArray = buffer.array();
        ByteBuffer data = DataType.BYTE_ARRAY.write(dataArray);
        ByteBuffer length = DataType.VAR_INT.write(dataArray.length + id.array().length);
        return ByteBufferHelper.combine(
                length,
                id,
                data
        );
    }
    Packet readDataFields(ByteBuffer buffer);
}
