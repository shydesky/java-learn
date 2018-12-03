package package17;

import java.util.stream.IntStream;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-25
 **/

public class Main {

  public static void main(String[] args) {
    InterfaceA inter_a = new ImplA();
    inter_a.func1();
    System.out.println(inter_a.func2());
    System.out.print("接口调用实现类的方法，首先将接口引用转化为实现类的类型");
    System.out.println(((ImplA) inter_a).func4());
    System.out.print("接口调用实现类的变量，首先将接口引用转化为实现类的类型");
    System.out.println(((ImplA) inter_a).public_a);

  }
}
