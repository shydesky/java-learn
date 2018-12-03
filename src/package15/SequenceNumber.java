package package15;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-30
 **/

public class SequenceNumber {
  private static ThreadLocal<Integer> number = new ThreadLocal<Integer>(){
    public Integer initialValue() {
      return 0;
    }
  };
  private static ThreadLocal<Integer> number2 = new ThreadLocal<Integer>(){
    public Integer initialValue() {
      return 0;
    }
  };

  public int getNextNumber(){
    number.set(number.get() + 1);
    return number.get();
  }

  public int getNextNumber2(){
    number2.set(number2.get() + 1);
    return number2.get();
  }

  private static int number3 = 0;  // 没有被ThreadLocal包裹

  public int getNextNumber3(){
    return number3++;
  }
}