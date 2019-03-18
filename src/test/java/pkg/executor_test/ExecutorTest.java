package test.java.pkg.executor_test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-04
 **/

public class ExecutorTest implements Application {

  public ExecutorService broadpool = Executors
      .newFixedThreadPool(2, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          return new Thread(r, "broad-msg");
        }
      });


  public static void func() {

  }

  public void shutdown() {
    broadpool.shutdown();
    //broadpool.shutdownNow();
    System.out.println("s");
  }
}