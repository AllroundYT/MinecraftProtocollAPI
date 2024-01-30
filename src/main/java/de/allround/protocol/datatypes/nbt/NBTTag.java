package de.allround.protocol.datatypes.nbt;

import java.nio.ByteBuffer;

public abstract class NBTTag<Type> {



    protected final Type data;

    protected NBTTag(Type data) {
        this.data = data;
    }


    @SuppressWarnings("unchecked")
    public static <Type> NBTTag<Type> fromId(byte id){
        return (NBTTag<Type>) switch (id){
            case 0x01 -> new NBTByte((byte) 0);
            case 0x02 -> new NBTShort((short) 0);
            default -> new NBTEnd();
        };
    }

    public abstract ByteBuffer write();

    public abstract NBTTag<Type> read(ByteBuffer buffer);

    public Type getData() {
        return data;
    }

}
