package de.allround.misc;


import de.allround.protocol.packets.PacketHandler;
import de.allround.protocol.packets.ReadablePacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;


public class ReadPacketTypeHandlerMap {
    private final Map<Class<? extends ReadablePacket>, List<PacketHandler<ReadablePacket>>> map;


    public ReadPacketTypeHandlerMap(ConcurrentMap<Class<? extends ReadablePacket>, List<PacketHandler<ReadablePacket>>> map) {
        this.map = map;
    }

    public ReadPacketTypeHandlerMap() {
        this.map = new ConcurrentHashMap<>();
    }

    public void remove(Class<? extends ReadablePacket> packetType, PacketHandler<ReadablePacket> t) {
        map.getOrDefault(packetType, Collections.emptyList()).remove(t);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final <PacketType extends ReadablePacket> void add(Class<PacketType> index, PacketHandler<PacketType>... objects) {
        map.compute(index, (packetType, ts) -> {
            if (ts == null) ts = new ArrayList<>();
            for (PacketHandler<PacketType> object : List.of(objects)) {
                if (!ts.contains(object)) {
                    ts.add((PacketHandler<ReadablePacket>) object);
                }
            }
            return ts;
        });
    }

    public ReadPacketTypeHandlerMap forEach(BiConsumer<Class<? extends ReadablePacket>, List<PacketHandler<ReadablePacket>>> consumer) {
        map.forEach(consumer);
        return this;
    }

    public List<PacketHandler<ReadablePacket>> get(Class<? extends ReadablePacket> index) {
        return map.getOrDefault(index, Collections.emptyList());
    }
}
