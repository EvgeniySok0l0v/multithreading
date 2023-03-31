package ru.clevertec.sokalau;

import java.util.concurrent.Callable;

public class Response implements Callable<Integer> {

    private final int val;

    public Response(int val) {
        this.val = val;

    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Response - Thread name:" + Thread.currentThread().getName() + "thread val:" + this.val);
        return this.val;
    }
}
