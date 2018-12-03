package test.java.pkg.volatile_test;

/**
 * @program: java-test-1
 * @description:  使用Enum实现单例模式
 * @author: shydesky@gmail.com
 * @create: 2018-12-03
 **/

public class SingletonWithEnum {

  public static SingletonWithEnum getInstance() {
    return SomeThing.INSTANCE.getInstance();
  }

  public enum SomeThing {
    INSTANCE;

    private SingletonWithEnum instance;

    //枚举的构造方法是私有的，只会调用一次
    SomeThing() {
      instance = new SingletonWithEnum();
      System.out.println("invoke once!");
    }

    public SingletonWithEnum getInstance() {
      System.out.println("return!");
      return instance;
    }
  }

  public static void main(String[] args) {
    SingletonWithEnum.getInstance();
  }
}