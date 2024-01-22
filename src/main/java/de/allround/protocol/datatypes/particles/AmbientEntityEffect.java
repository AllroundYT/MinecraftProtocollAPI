package de.allround.protocol.datatypes.particles;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.Identifier;

import java.nio.ByteBuffer;

public class AmbientEntityEffect extends Particle {
    protected AmbientEntityEffect() {
        super(Identifier.minecraft("ambient_entity_effect"), (byte) 0, new DataType[0]);
    }

    public ByteBuffer write(){
        return _write(new Object[0]);
    }

    public void read(ByteBuffer buffer){
        _read(buffer);
    }
}
