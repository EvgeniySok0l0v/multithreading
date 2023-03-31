package ru.clevertec.sokalau;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private final List<Integer> integers = new ArrayList<>();

    public Response response(Request request) throws InterruptedException {
        long sleep = 100 + (int)(Math.random() * 1000);
        Thread.sleep(sleep);
        System.out.println("Sleep time:" + sleep);
        integers.add(request.call());
        return new Response(integers.size());
    }

    public List<Integer> getIntegers() {
        return integers;
    }
}
