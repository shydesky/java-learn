package package_one;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-07-16
 **/

public class Trans {

    public synchronized void printNum(int num){
        System.out.print(Thread.currentThread());//获取当前运行这个方法的类
        for(int i=0;i<25;i++){
            System.out.print(i+" ");
        }
        System.out.println();
    }
}

class MyThread implements Runnable {
    private Trans trans;
    private int num;

    public MyThread(Trans trans, int num) {
        this.trans = trans;
        this.num = num;
    }

    public void run() {
        trans.printNum(num);
    }
}

class MyThread2 implements Runnable {
    private Trans trans;
    private ConcurrentHashMap map;
    private int ss;

    public MyThread2(ConcurrentHashMap<String, Integer> map, int ss) {
        this.map = map;
        this.ss = ss;
    }

    public void run() {
        for(int i=0; i<10; i++){
            map.put(String.valueOf(i+ss) ,i+ss);
        }
    }
}
