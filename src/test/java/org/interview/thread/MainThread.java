package org.interview.thread;

/**
 * 打印出HelloWorld中的主线程数
 *
 * @author dong
 * @create 2018-07-26 下午2:12
 **/
public class MainThread {

    public static void main(String[] args) {
        System.out.println("hello world");
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while(group != null) {
            topGroup = group;
            group = group.getParent();
        }
        int nowThreads = topGroup.activeCount();
        Thread[] lstThreads = new Thread[nowThreads];
        topGroup.enumerate(lstThreads);
        for(int i = 0; i< nowThreads; i++) {
            System.out.println("线程number:" + i + " = " + lstThreads[i].getName());
        }
    }

}
