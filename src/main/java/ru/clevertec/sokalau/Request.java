package ru.clevertec.sokalau;

import java.util.concurrent.Callable;

public class Request implements Callable<Integer> {

    private final Integer val;

    public Request(int value){
        this.val = value;
    }
    @Override
    public Integer call() {
        System.out.println("Request - Thread name:" + Thread.currentThread().getName() + "thread val:" + this.val);
        return this.val;
    }
}
