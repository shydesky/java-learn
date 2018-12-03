package package15;

/**
 * @program: java-test-1
 * @description: 测试ThreadLocal
 * @author: shydesky@gmail.com
 * @create: 2018-09-30
 **/

public class TestThreadLocal {


  public static void main(String[] args) {
    SequenceNumber sn = new SequenceNumber();  //线程共享的变量
    SequenceNumber sn2 = new SequenceNumber();
    Client t1 = new Client(sn);
    Client t2 = new Client(sn2);
    Client t3 = new Client(sn);
    t1.start();
    t2.start();
    t3.start();
  }
}

class Client extends Thread{
  SequenceNumber sn;
  public Client(SequenceNumber sn){
    this.sn = sn;
  }

  @Override
  public void run() {
    //System.out.println(sn.hashCode());
    //System.out.println(sn.toString());

    for (int i = 0; i < 5; i++) {
      System.out.println("Thread[" + this.getName() + "] print numbder:" + sn.getNextNumber());
      System.out.println("Thread[" + this.getName() + "] print number2:" + sn.getNextNumber2());
    }

    for (int i = 0; i < 5; i++) {
      System.out.println("Thread[" + this.getName() + "] print number2:" + sn.getNextNumber2());
    }
  }
}