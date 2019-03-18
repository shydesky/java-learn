package package20;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2019-03-18
 **/

//受限泛型
public class Demo2{

  public static void main(String args[]) {
    Info<Father> i = new Info<Father>();        // 声明Father的泛型对象
    i.setVar(new Father());                     // 设置Father
    fun(i);

    Info<Child> j = new Info<Child>();        // 声明Child的泛型对象
    j.setVar(new Child());                     // 设置Father
    fun(j);
  }

  public static void fun(Info<? super Child> temp) {  // 只能接收Child及其父类
    System.out.print(temp + "、");
  }
}




