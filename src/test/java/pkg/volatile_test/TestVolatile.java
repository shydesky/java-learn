package test.java.pkg.volatile_test;

import java.util.ArrayList;

/**
 * @program: java-test-1
 * @description: 测试volatile关键字
 * @author: shydesky@gmail.com
 * @create: 2018-12-03
 **/

public class TestVolatile {

  public static void main(String[] args) {
    while (true) {
      long t1 = System.nanoTime();
      ArrayList<Thread> alist = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        alist.add(new Thread() {
          public void run() {
            Singleton o = Singleton.getInstance2();
            System.out.println(o);
          }
        });
      }

      for (Thread t : alist) {
        t.start();
      }

      for (Thread t : alist) {
        try {
          t.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println(System.nanoTime() - t1);
    }
  }
}