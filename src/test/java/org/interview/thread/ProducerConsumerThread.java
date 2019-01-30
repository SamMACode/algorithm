package org.interview.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者Producer和消费者Consumer线程协调机制
 *
 * @author dong
 * @create 2018-07-28 下午1:45
 **/
public class ProducerConsumerThread {

    private Integer[] sharedData = new Integer[5];
    // 创建一个可重入锁ReentrantLock.
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    // 表示数组中元素的数量.
    private volatile Integer arrayNums = 0;

    public void testMultiThreads() {
        Producer producer = new Producer(sharedData, notFull, notEmpty, arrayNums, "producer-Thread", lock);
        Consumer consumer = new Consumer(sharedData, notFull, notEmpty, arrayNums, "consumer-thread", lock);
        System.out.println("线程协调开始...");
        consumer.start();
        producer.start();
    }

    public static void main(String[] args) {
        new ProducerConsumerThread().testMultiThreads();
    }
}


/**
 * 创建生产者用于向共享数组中放入元素.
 * */
class Producer extends Thread {

    private Integer[] data;
    private final Condition notFull;
    private final Condition notEmpty;
    private volatile Integer nums = 0;
    private final Lock lock;

    Producer(Integer[] sharedData, Condition notfull, Condition notempty, Integer arrayNums, String threadName, Lock lock) {
        super(threadName);
        this.data = sharedData;
        this.notFull = notfull;
        this.notEmpty = notempty;
        this.nums = arrayNums;
        this.lock = lock;
    }

    @Override
    public void run() {
        /*synchronized (data) {
            nums++;
        }*/
        try {
            lock.lock();
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            while(true) {
                if (nums == 5) notFull.await();
                for(int i = 0; i < 5; i++) {
                    data[i] = new Random().nextInt(10);
                }
                System.out.println("[putItem]生成数组的内容为:" + Arrays.toString(data));
                nums = 5;
                notEmpty.signal();          // 共享数组中已经填充满数据.
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 创建消费者线程.
 * */
class Consumer extends Thread {
    private Integer[] data;
    private final Condition notEmpty;
    private final Condition notFull;
    private volatile Integer nums;
    private final Lock lock;

    Consumer(Integer[] sharedData, Condition notFull, Condition notEmpty, Integer arrayNums, String threadName, Lock lock) {
        super(threadName);
        this.data = sharedData;
        this.notFull = notFull;
        this.notEmpty = notEmpty;
        this.nums = arrayNums;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            while(true) {
                if (nums == 0) notEmpty.await();
                System.out.println("[take]取出数组中所有的元素: " + Arrays.toString(data));
                for(int i = 0; i < 5; i++) {
                    data[i] = null;
                }
                nums = 0;
                notFull.signal();          // 共享数组中已经填充满数据.
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        } finally {
            lock.unlock();
        }
    }
}