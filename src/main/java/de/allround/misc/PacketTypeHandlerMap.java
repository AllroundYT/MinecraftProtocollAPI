package de.allround.misc;


import de.allround.protocol.packets.Packet;
import de.allround.protocol.packets.PacketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;


public class PacketTypeHandlerMap {
    private final ConcurrentMap<Class<? extends Packet>, List<PacketHandler<Packet>>> map;


    public void remove(Class<? extends Packet> packetType, PacketHandler<Packet> t){
        map.getOrDefault(packetType, Collections.emptyList()).remove(t);
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public PacketTypeHandlerMap(ConcurrentMap<Class<? extends Packet>, List<PacketHandler<Packet>>> map) {
        this.map = map;
    }

    public PacketTypeHandlerMap() {
        this.map = new ConcurrentHashMap<>();
    }

    @SafeVarargs
    public final <PacketType extends Packet> void add(Class<PacketType> index, PacketHandler<PacketType>... objects) {
        map.compute(index, (packetType, ts) -> {
            if (ts == null) ts = new ArrayList<>();
            for (PacketHandler<PacketType> object : List.of(objects)) {
                if (!ts.contains(object)) {
                    ts.add((PacketHandler<Packet>) object);
                }
            }
            return ts;
        });
    }

    public PacketTypeHandlerMap forEach(BiConsumer<Class<? extends Packet>, List<PacketHandler<Packet>>> consumer) {
        map.forEach(consumer);
        return this;
    }

    public List<PacketHandler<Packet>> get(Class<? extends Packet> index) {
        return map.getOrDefault(index, Collections.emptyList());
    }
}
