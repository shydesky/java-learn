package test.java.pkg.executor_test;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-05
 **/

public class Task implements Runnable {
   private volatile boolean canceled;
   public String[] a;
   public Task(String[] a){
     this.a = a;
   }
   public void run(){



   }

   public void cancel(){
     canceled = true;
   }
}