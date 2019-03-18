package package_volatile;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-14
 **/

public class SingleTon {

  private static SingleTon instance = null;
  private String str = "sun";

  public SingleTon(String str){


    this.str = str;
  }

  public static void releaseInstance(){
    instance = null;
  }

  public String getStr(){
    return this.str;
  }

  public static SingleTon getInstance() {
    if (instance == null) {
      synchronized (SingleTon.class) {
        if (instance == null) {
          System.out.println("s");
          instance = new SingleTon("sunhaoyu");
        }
      }
    }
    return instance;
  }
}