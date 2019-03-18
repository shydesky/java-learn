package test.java.pkg.executor_test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-12-04
 **/

public class Main {

  public static void main(String[] args) {
    ExecutorTest executor = new ExecutorTest();

    executor.broadpool.submit(() -> {
      try {
        while (true) {
          Thread.sleep(1000);
          //consumerAdvObjToSpread();
        }
      } catch (InterruptedException e) {

      }
    });

    executor.broadpool.submit(() -> {
      try {
        while (true) {
          Thread.sleep(1000);
          //consumerAdvObjToSpread();
        }
      } catch (InterruptedException e) {

      }
    });

    Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));

  }
}