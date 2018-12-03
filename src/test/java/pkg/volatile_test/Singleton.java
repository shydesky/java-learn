package test.java.pkg.volatile_test;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-03
 * @ref: 参考文档: http://chen-tao.github.io/2016/12/30/about-java-singleton/
 **/

public class Singleton {

  private static volatile Singleton instance = null;   //此处需要用volatile进行修饰，是防止指令重排序

  private Singleton() {

  }

  //每次获取对象的时候，都需要进行synchronized线程同步，效率较低，实际情况是单例模式很少new对象。
  public static Singleton getInstance1() {
    synchronized (Singleton.class) {
      if (instance == null) {
        instance = new Singleton();
      }
    }
    return instance;
  }


  //单例模式的双检查（double-check），提高了效率，应该使用这种方式。
  //双重检查原因：A、B两个线程同时进入到if，执行到竞争synchronized锁的代码，A抢到锁之后，初始化了instance，B再次初始化。
  public static Singleton getInstance2() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null) {
          instance = new Singleton();
        }
      }
    }
    return instance;
  }

  //懒汉式，线程不安全
  public static Singleton getInstance3() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }

  //懒汉式，线程安全，效率低
  public synchronized static Singleton getInstance4() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
}