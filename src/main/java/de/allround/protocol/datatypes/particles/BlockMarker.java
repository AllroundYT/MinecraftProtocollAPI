package de.allround.protocol.datatypes.particles;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.Identifier;

import java.nio.ByteBuffer;

public class BlockMarker extends Particle {
    protected int blockStateId;

    protected BlockMarker() {
        super(Identifier.minecraft("block_marker"), (byte) 3, new DataType[]{DataType.VAR_INT});
    }

    public ByteBuffer write(){
        return _write(new Object[]{blockStateId});
    }

    public void read(ByteBuffer buffer){
        assert _read(buffer) != null;
        blockStateId = (int) _read(buffer)[0];
    }
}
