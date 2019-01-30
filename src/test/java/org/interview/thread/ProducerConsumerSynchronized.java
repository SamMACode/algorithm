package org.interview.thread;

import java.util.Arrays;
import java.util.Random;

/**
 * 使用同步代码块synchronized进行协调
 *
 * @author dong
 * @create 2018-07-28 下午2:54
 **/
public class ProducerConsumerSynchronized {

    private Integer[] sharedData = new Integer[5];
    private volatile Integer arrayNums = 0;
    private Object locks = new Object();

    public void testMultiThreadCoordinate() {
        Producers producer = new Producers(sharedData, arrayNums, locks);
        Consumers consumer = new Consumers(sharedData, arrayNums, locks);
        System.out.println("线程协调开始coordinate...");
        consumer.start();
        producer.start();
    }

    public static void main(String[] args) {
        new ProducerConsumerSynchronized().testMultiThreadCoordinate();
    }
}

class Producers extends Thread {
    private Integer[] data;
    private volatile Integer nums;
    private final Object lock;

    public Producers(Integer[] arrayData, Integer arrayNums, Object locks) {
        this.data = arrayData;
        this.nums = arrayNums;
        this.lock = locks;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    if (nums == 5) this.lock.wait();
                    for (int i = 0; i < 5; i++) {
                        data[i] = new Random().nextInt(10);
                    }
                    System.out.println("[putItem]产生了数据data: " + Arrays.toString(data));
                    nums = 5;
                    // 当在共享数组中填充了所有的数据之后,唤醒其他所有在等待的线程.
                    lock.notifyAll();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Consumers extends Thread {
    private Integer[] data;
    private volatile Integer nums;
    private final Object lock;

    public Consumers(Integer[] arrayData, Integer arrayNums, Object locks) {
        this.data = arrayData;
        this.nums = arrayNums;
        this.lock = locks;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    if (nums == 0) this.lock.wait();
                    System.out.println("[take]所有数据data: " + Arrays.toString(data));
                    for (int i = 0; i < 5; i++) {
                        data[i] = null;
                    }
                    nums = 0;           // 取出来数据之后设置volatile数值为0.
                    // 在取出所有的数据之后,唤醒生产者线程.
                    lock.notifyAll();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}