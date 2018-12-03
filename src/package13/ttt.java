package package13;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import org.joda.time.DateTime;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-16
 **/

public class ttt {

  public static void main(String[] args) {
    byte[] ba = "hello,world".getBytes();
    byte[] b = new byte[10];

    /*System.arraycopy(ba, 4, b, 4, 5);
    System.out.println(new String(ba));
    System.out.println(new String(b));
    DateTime time = DateTime.now();
    System.out.println(time);
    System.out.println(time.getSecondOfMinute());
    System.out.println(time.getMinuteOfHour());
    System.out.println(time.getMinuteOfDay());
    System.out.println(3000 - (time.getSecondOfMinute() * 1000 + time.getMillisOfSecond())%3000);
*/
    testHashmapComputeIfAbsent();
  }

  public static void testHashmapComputeIfAbsent(){
    HashMap<Long, ArrayList<Integer>> hash = new HashMap<>();
    hash.computeIfAbsent(1L, ls->new ArrayList<>());
    hash.get(1L).add(1);
    hash.get(1L).add(2);
    hash.get(1L).add(3);

    System.out.println(hash.get(1L).size());
    System.out.println(hash.get(1L).removeIf(b->b.equals(2)));
    System.out.println(hash.get(1L).getClass());
    System.out.println(new String("ss".getBytes()));

  }

  Reference<Integer> parent = new WeakReference<>(null);


}