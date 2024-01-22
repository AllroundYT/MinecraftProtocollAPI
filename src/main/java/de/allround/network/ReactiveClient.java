package de.allround.network;

import de.allround.misc.Future;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ReactiveClient {

    private final AsynchronousSocketChannel socketChannel;
    private final List<Consumer<ByteBuffer>> dataConsumers;
    private final List<Consumer<Throwable>> throwableConsumers;
    private int bufferSize = 1024;

    public int getBufferSize() {
        return bufferSize;
    }

    public ReactiveClient setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public ReactiveClient(int bufferSize) {
        try {
            this.bufferSize = bufferSize;
            this.socketChannel = AsynchronousSocketChannel.open();
            this.dataConsumers = Collections.synchronizedList(new ArrayList<>());
            this.throwableConsumers = Collections.synchronizedList(new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ReactiveClient() {
        try {
            this.socketChannel = AsynchronousSocketChannel.open();
            this.dataConsumers = Collections.synchronizedList(new ArrayList<>());
            this.throwableConsumers = Collections.synchronizedList(new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ReactiveClient handle(Consumer<ByteBuffer> consumer){
        dataConsumers.add(consumer);
        return this;
    }

    public ReactiveClient handleThrowable(Consumer<Throwable> consumer){
        throwableConsumers.add(consumer);
        return this;
    }

    public void close(){
        try {
            if (socketChannel.isOpen()) socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Future<Integer> write(ByteBuffer buffer){
        return Future.future(promise -> socketChannel.write(buffer, null, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Object attachment) {

                promise.complete(result);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

                promise.complete(exc);
            }
        }));
    }

    public Future<Integer> write(byte[] data){
        return write(ByteBuffer.wrap(data));
    }

    public Future<Void> connect(String host, int port){
        return Future.future(promise -> {
            socketChannel.connect(InetSocketAddress.createUnresolved(host, port));
            promise.complete();

            while (socketChannel.isOpen()){
                ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
                socketChannel.read(buffer, null, new CompletionHandler<>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        dataConsumers.forEach(byteBufferConsumer -> byteBufferConsumer.accept(buffer));
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        throwableConsumers.forEach(consumer -> consumer.accept(exc));
                    }
                });
            }
        });
    }
}
