package package_volatile;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-14
 **/

public class Main {

  public static void main(String[] args) {
    int i = 0;
    while (i < 10) {
      i++;
      loop();
      SingleTon.releaseInstance();
    }
  }

  public static void loop() {

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        SingleTon.getInstance();
      }
    });

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        SingleTon instance = SingleTon.getInstance();
        if (instance.getStr().equals("sun")) {
          System.out.println("bug");
        }
      }
    });
    thread1.start();
    thread2.start();
  }
}



