package package15;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-09
 **/

public class TestHashCode {

  public static void main(String[] args) {
    Map<String, String> activePeers = new ConcurrentHashMap<>();
    activePeers.put("123","1");
    System.out.println(activePeers.hashCode());
    byte [] input = new byte [] {0, 1, 2, 3, 4, 5, 6, 7};
    byte [] result = new byte[2];
    System.arraycopy(input, 6, result, 0, 2);
    System.out.println(result[1]);
  }
}