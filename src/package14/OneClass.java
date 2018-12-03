package package14;

import com.sun.org.apache.bcel.internal.classfile.InnerClass;
import java.util.ArrayList;
import package13.FileUtil;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-27
 **/

public class OneClass {

  private int i=0;
  private static int s_i=1;

  public void foo1(){
    OneClass one = new OneClass();
    OneClass.InnerClass1 iner1 = new OneClass.InnerClass1();
    iner1.print_i();
  }

  public void foo3() {
    InnerClass3 iner3=new InnerClass3();
  }

  //成员内部类，作为外部类的一个成员。
  public class InnerClass1{
    // private static int k=0;  //不能在内部类中定义静态变量和静态方法，除非内部类是静态内部类。
    public InnerClass1(){

    }
    public void print_i(){
      System.out.println(s_i);  //可以访问外部类的静态变量
      System.out.println(i);  //也可以访问外部类的非静态变量
    }
  }


  //局部内部类，作为外部类的一个成员方法的元素
  public void foo2(){
    int i=2;
    int j=4;
    class InnerClass2{
      private int p=1;
      private int i=3;
      /*
      private static void ss(){  //不能定义静态方法
      }
      */
      private void func(){
        System.out.println(i);  //访问内部类和外部类同名的变量  3
        System.out.println(j);  // 访问所在成员方法的局部变量  3
        System.out.println(OneClass.this.i);  // 访问外部类和内部类同名的变量  0
      }
    }
    InnerClass2 iner2 = new InnerClass2();
    iner2.func();
  }

  static class InnerClass3{
    public InnerClass3() {

    }
  }

  public static void main(String[] args) {
    OneClass one = new OneClass();
    one.foo1();
    one.foo2();
  }
}