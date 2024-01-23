package de.allround.protocol.datatypes;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.StringReader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface DataType<T> {

    DataType<Boolean> BOOLEAN = new DataType<>() {
        @Contract(pure = true)
        @Override
        public @NotNull ByteBuffer write(Boolean aBoolean) {
            return ByteBuffer.allocate(1).put(0, (byte) (aBoolean ? 1 : 0));
        }

        @Contract(pure = true)
        @Override
        public @NotNull Boolean read(@NotNull ByteBuffer buffer) {
            byte value = buffer.get();
            return value == 0x01 ? Boolean.TRUE : (value == 0x00 ? Boolean.FALSE : null);
        }
    };

    DataType<Byte> BYTE = new DataType<>() {
        @Contract("_ -> new")
        @Override
        public @NotNull ByteBuffer write(Byte aByte) {
            return ByteBuffer.allocate(1).put(0, aByte);
        }

        @Override
        public @NotNull Byte read(@NotNull ByteBuffer buffer) {
            return buffer.get();
        }
    };

    DataType<Short> SHORT = new DataType<>() {
        @Override
        public @NotNull ByteBuffer write(Short aShort) {
            return ByteBuffer.allocate(2).putShort(0, aShort);
        }

        @Override
        public @NotNull Short read(@NotNull ByteBuffer buffer) {
            return buffer.getShort();
        }
    };

    DataType<Integer> INT = new DataType<>() {
        @Override
        public @NotNull ByteBuffer write(Integer integer) {
            return ByteBuffer.allocate(4).putInt(0, integer);
        }

        @Override
        public @NotNull Integer read(@NotNull ByteBuffer buffer) {
            return buffer.getInt();
        }
    };

    DataType<Long> LONG = new DataType<>() {
        @Override
        public @NotNull ByteBuffer write(Long aLong) {
            return ByteBuffer.allocate(8).putLong(0, aLong);
        }

        @Override
        public @NotNull Long read(@NotNull ByteBuffer buffer) {
            return buffer.getLong();
        }
    };

    DataType<Float> FLOAT = new DataType<Float>() {
        @Override
        public @NotNull ByteBuffer write(Float aFloat) {
            return ByteBuffer.allocate(4).putFloat(0, aFloat);
        }

        @Override
        public @NotNull Float read(@NotNull ByteBuffer buffer) {
            return buffer.getFloat();
        }
    };

    DataType<Double> DOUBLE = new DataType<Double>() {
        @Override
        public @NotNull ByteBuffer write(Double aDouble) {
            return ByteBuffer.allocate(8).putDouble(0, aDouble);
        }

        @Override
        public @NotNull Double read(@NotNull ByteBuffer buffer) {
            return buffer.getDouble();
        }
    };
    int JSON_MAX_LENGTH = 262144;
    int IDENTIFIER_MAX_LENGTH = 32767;
    //var int/ var long quelle: https://wiki.vg/Protocol#VarInt_and_VarLong (21.01.2024 14:40)
    int CONTINUE_BIT = 0x80;
    int SEGMENT_BITS = 0x7F;
    DataType<Integer> VAR_INT = new DataType<Integer>() {
        @Override
        public @NotNull ByteBuffer write(Integer value) {
            List<Byte> bytes = new ArrayList<>();
            while (true) {
                if ((value & ~SEGMENT_BITS) == 0) {
                    bytes.add(value.byteValue());
                    //buffer.put(index,value.byteValue());
                    break;
                }

                bytes.add((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));
                //buffer.put(index,(byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
            }


            ByteBuffer buffer = ByteBuffer.allocate(bytes.size());
            for (int i = 0; i < bytes.size(); i++) {
                buffer.put(i, bytes.get(i));
            }
            return buffer;
        }

        @Override
        public Integer read(@NotNull ByteBuffer buffer) {
            int value = 0;
            int position = 0;
            byte currentByte;

            while (true) {
                currentByte = buffer.get();
                value |= (currentByte & SEGMENT_BITS) << position;

                if ((currentByte & CONTINUE_BIT) == 0) break;

                position += 7;

                if (position >= 32) throw new RuntimeException("VarInt is too big");
            }

            return value;
        }
    };
    DataType<String> STRING = new DataType<>() {
        @Override
        public ByteBuffer write(@NotNull String s) {
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            ByteBuffer lengthBuffer = DataType.VAR_INT.write(bytes.length);
            byte[] lengthAsVarInt = lengthBuffer.array();
            return ByteBuffer.allocate(lengthAsVarInt.length + bytes.length)
                             .put(0, lengthAsVarInt)
                             .put(lengthAsVarInt.length, bytes);
        }

        @Override
        public @NotNull String read(@NotNull ByteBuffer buffer) {
            int lengthValue = DataType.VAR_INT.read(buffer);
            byte[] stringBytes = new byte[lengthValue];
            buffer.get(stringBytes);
            return new String(stringBytes);
        }
    };
    //chat - nbt
    DataType<JsonElement> JSON = new DataType<>() {
        @Override
        public ByteBuffer write(@NotNull JsonElement jsonElement) {
            System.out.println(jsonElement.toString());
            return DataType.STRING.write(jsonElement.toString());
        }

        @Override
        public JsonElement read(ByteBuffer buffer) {
            String value = DataType.STRING.read(buffer);
            JsonReader jsonReader = new JsonReader(new StringReader(value));
            jsonReader.setLenient(true);
            return JsonParser.parseReader(jsonReader);
        }
    };
    DataType<Identifier> IDENTIFIER = new DataType<Identifier>() {
        @Override
        public ByteBuffer write(@NotNull Identifier identifier) {
            return DataType.STRING.write(String.copyValueOf(identifier.toString().toCharArray(),
                                                            0,
                                                            Math.min(identifier.toString().length(),
                                                                     IDENTIFIER_MAX_LENGTH
                                                            )
            ));

        }

        @Override
        public @NotNull Identifier read(ByteBuffer buffer) {
            String string = DataType.STRING.read(buffer);
            if (string == null) {
                throw new IllegalStateException("Identity string cannot be null.");
            }
            return Identifier.of(string);
        }
    };
    DataType<Long> VAR_LONG = new DataType<Long>() {
        @Override
        public @NotNull ByteBuffer write(Long value) {
            List<Byte> bytes = new ArrayList<>();
            while (true) {
                if ((value & ~((long) SEGMENT_BITS)) == 0) {
                    bytes.add(value.byteValue());
                    break;
                }

                bytes.add((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

                // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
                value >>>= 7;
            }

            ByteBuffer buffer = ByteBuffer.allocate(bytes.size());
            for (int i = 0; i < bytes.size(); i++) {
                buffer.put(i, bytes.get(i));
            }
            return buffer;
        }

        @Override
        public Long read(@NotNull ByteBuffer buffer) {
            long value = 0;
            int position = 0;
            byte currentByte;

            while (true) {
                currentByte = buffer.get();
                value |= (long) (currentByte & SEGMENT_BITS) << position;

                if ((currentByte & CONTINUE_BIT) == 0) break;

                position += 7;

                if (position >= 64) throw new RuntimeException("VarLong is too big");
            }

            return value;
        }
    };


    //entity metadata
    //slot
    //nbt tag
    DataType<Position> POSITION = new DataType<>() {
        @Override
        public @NotNull ByteBuffer write(@NotNull Position position) {
            long value = ((position.x() & 0x3FFFFFF) << 38) | ((position.z() & 0x3FFFFFF) << 12) | (position.y() & 0xFFF);
            return ByteBuffer.allocate(8).putLong(0, value);
        }

        @Override
        public @NotNull Position read(@NotNull ByteBuffer buffer) {
            long val = buffer.getLong();
            long x = val >> 38;
            long y = val << 52 >> 52;
            long z = val << 26 >> 38;

            return new Position(x, y, z);
        }
    };
    //angle


    DataType<UUID> UUID = new DataType<>() {
        @Override
        public @NotNull ByteBuffer write(@NotNull UUID uuid) {
            return ByteBuffer.allocate(16).putLong(0, uuid.getMostSignificantBits()).putLong(8,
                                                                                             uuid.getLeastSignificantBits()
            );
        }

        @Override
        public @NotNull UUID read(@NotNull ByteBuffer buffer) {
            long mostSignificantBits = buffer.getLong();
            long leastSignificantBits = buffer.getLong();
            return new UUID(mostSignificantBits, leastSignificantBits);
        }
    };

    //optional x
    //array of x
    //x enum

    DataType<byte[]> BYTE_ARRAY = new DataType<byte[]>() {
        @Override
        public ByteBuffer write(byte @NotNull [] bytes) {
            return ByteBuffer.allocate(bytes.length).put(0, bytes);
        }

        @Contract(pure = true)
        @Override
        public byte @NotNull [] read(@NotNull ByteBuffer buffer) {
            return buffer.array();
        }
    };

    static int fixedPointNumber(double d){
        return (int) (d * 32.0D);
    }

    static double fixedPointNumber(int i){
        return i / 32.0D;
    }

    ByteBuffer write(T t);

    T read(ByteBuffer buffer);

    default T read(ByteBuffer buffer, int size){
        return null;
    }
}
