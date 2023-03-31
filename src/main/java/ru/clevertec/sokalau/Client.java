package ru.clevertec.sokalau;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Client {

    private final List<Integer> integers = new ArrayList<>();
    private final int n = 10;
    private final Server server = new Server();
    private static final AtomicInteger accumulator = new AtomicInteger(0);
    private static final ReentrantLock lock = new ReentrantLock();
    public Client() {
        for (int i = 1; i <= n; i++){
            integers.add(i);
        }
        System.out.println("Start size = " + integers.size());
    }

    public void request(){
        ExecutorService executor = Executors.newFixedThreadPool(n);

        IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    try {
                        return executor.submit(server.response(new Request(remove((int) (Math.random() * integers.size())))));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(i -> {Runnable task = () ->
                        {
                            try {
                                accumulator.accumulateAndGet(i.get(), Integer::sum);
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        };
                            executor.submit(task);
                        }
                );

        executor.shutdown();

        server.getIntegers().forEach(System.out::println);

        System.out.println("End size = " + integers.size());
        System.out.println("acum: = " + accumulator);
    }

    private int remove(int i){
        lock.lock();
        try {
            return integers.remove(i);
        } finally {
            lock.unlock();
        }
    }
}
