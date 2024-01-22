package de.allround.protocol.datatypes.particles;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.Identifier;

import java.nio.ByteBuffer;

public class Block extends Particle {
    protected int blockStateId;

    protected Block() {
        super(Identifier.minecraft("block"), (byte) 2, new DataType[]{DataType.VAR_INT});
    }

    public ByteBuffer write(){
        return _write(new Object[]{blockStateId});
    }

    public void read(ByteBuffer buffer){
        assert _read(buffer) != null;
        blockStateId = (int) _read(buffer)[0];
    }
}
