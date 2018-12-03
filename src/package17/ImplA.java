package package17;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-25
 **/

public class ImplA implements InterfaceA {

  @Override
  public void func1() {
    System.out.println("i am func1");
  }

  @Override
  public String func2() {
    return "i am func2";
  }

  private int func3(){
    return 100;
  }

  public int func4(){
    return 100;
  }

  public int public_a = 3;

  public static void main(String[] args) {
    InterfaceA inter_a = new ImplA();
    ((ImplA) inter_a).func3();
  }
}