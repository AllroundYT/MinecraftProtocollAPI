package de.allround.protocol.datatypes;

import java.nio.ByteBuffer;

public class NBT {


    public static abstract class Tag<Type> {

        public static Tag<Byte> BYTE(byte b){
            return new Tag<>(b) {
                @Override
                public ByteBuffer write() {
                    return null;
                }
            };
        }

        public abstract ByteBuffer write();

        private Tag(Type data) {
            this.data = data;
        }
        private final Type data;

        public Type getData() {
            return data;
        }
    }
}
